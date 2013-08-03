/**
 * $Id$
 * Copyright 2009-2012 Oak Pacific Interactive. All rights reserved.
 */
package com.r.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.r.PrefixAnalyzer;

/**
 * 
 * 
 * @author <a href="mailto:rebricate@gmail.com">刘刚</a>
 * @version 2013-6-28下午3:23:30
 */
public class ChineseDemo {

    private static String[] strings = { "道德经", "daodejing","ddj" };

    private static Analyzer[] analyzers = { 
            new PrefixAnalyzer() };

    public static void analyzerTest(String filePath) {
        try {
            String text = readText(filePath);
            for (Analyzer analyzer : analyzers)
                analyze(text, analyzer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readText(String filePath) {
        try {
            return readText(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readText(InputStream input) {
        try {

            InputStreamReader inReader = new InputStreamReader(input, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inReader);
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void analyze(String string, Analyzer analyzer) throws IOException {
        StringBuffer buffer = new StringBuffer();
        
        
        TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(string));
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            buffer.append("[");
            buffer.append(charTermAttribute.toString());
            buffer.append("]");
        }
       

        String output = buffer.toString();
        System.out.println(output);
    }
    public static void main(String args[]) throws Exception {

        for (String string : strings) {
            for (Analyzer analy : analyzers) {
                analyze(string, analy);
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date beginDate = df.parse("2013-07-10 23:59:59");
        System.out.println(beginDate.getTime());

    }
}
