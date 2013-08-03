/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.r;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeSource;


/**
 * 
 * 
 * @author <a href="mailto:rebricate@gmail.com">刘刚</a>
 * @version 2013-7-30下午3:25:50
 */

public final class PrefixTokenizer extends Tokenizer {


    public PrefixTokenizer(Reader in) {
      super(in);
    }

    public PrefixTokenizer(AttributeSource source, Reader in) {
      super(source, in);
    }

    public PrefixTokenizer(AttributeFactory factory, Reader in) {
      super(factory, in);
    }
       
    private int offset = 0, dataLen=0;
    private final static int MAX_WORD_LEN = 255;
    private final static int IO_BUFFER_SIZE = 1024;
    private final char[] ioBuffer = new char[IO_BUFFER_SIZE];


    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    
    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();

        while (true) {

            //读取输入流，当处理完一次输入流后，在读输入流datalen就等于-1
            if (offset >= dataLen) {
                dataLen = input.read(ioBuffer);
                offset = 0;
            }

            if (dataLen == -1 || dataLen > MAX_WORD_LEN) {
               return false;
            }  
            //偏移量加一，写入索引词典
            offset++;
            termAtt.copyBuffer(ioBuffer, 0, offset);
            offsetAtt.setOffset(correctOffset(0), correctOffset(offset));
            return true;
        }
    }
    
    @Override
    public final void end() {
      // set final offset
      final int finalOffset = correctOffset(offset);
      this.offsetAtt.setOffset(finalOffset, finalOffset);
    }

    @Override
    public void reset() throws IOException {
      super.reset();
      offset = dataLen = 0;
    }
}

