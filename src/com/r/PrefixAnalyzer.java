/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.r;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;


/**
 * @author <a href="mailto:rebricate@gmail.com">刘刚</a>
 * @version 2013-7-30下午3:26:25
 */
public final class PrefixAnalyzer extends Analyzer {

    /**
     * Creates
     * {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents}
     * used to tokenize all the text in the provided {@link Reader}.
     * 
     * @return {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents}
     *         built from a {@link PrefixTokenizer} filtered with
     *         {@link PrefixFilter}
     */
      @Override
      protected TokenStreamComponents createComponents(String fieldName,
          Reader reader) {
        final Tokenizer source = new PrefixTokenizer(reader);
        return new TokenStreamComponents(source);
      }
  }
