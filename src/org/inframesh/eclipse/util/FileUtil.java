package org.inframesh.eclipse.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/** *//**
 *
 * @author 1238855
 */
public class FileUtil {
    public static String read(String fileName) throws IOException {
        return read(fileName, null);
    }    
    public static String read(String fileName, String charsetName) throws IOException {
        InputStream in = new FileInputStream(fileName);
        InputStreamReader reader = null;
        StringBuffer sb = new StringBuffer("");
        if(charsetName == null)
            reader =  new InputStreamReader(in);
        else
            reader = new InputStreamReader(in, charsetName);
        BufferedReader br = new BufferedReader(reader);
        String data;
        while((data=br.readLine())!=null)
            sb.append(data);
        br.close();
        return sb.toString();
    }
    
    
    public void copyTextFile(String from, String charsetFrom, String to, String charsetTo)
            throws IOException {
        InputStream in = new FileInputStream(from);
        InputStreamReader reader = null;
        if(charsetFrom==null)
            reader = new InputStreamReader(in);
        else
            reader = new InputStreamReader(in, charsetFrom);
        BufferedReader br = new BufferedReader(reader);
        
        OutputStream out = new FileOutputStream(to);
        OutputStreamWriter writer = null;
        if(charsetTo==null)
            writer = new OutputStreamWriter(out);
        else
            writer = new OutputStreamWriter(out, charsetTo);
        BufferedWriter bw = new BufferedWriter(writer);
        PrintWriter pw = new PrintWriter(bw, true);
        
        String data;
        while((data=br.readLine())!=null)
            pw.println(data);
        br.close();
        pw.close();
    }
    
//    public static void main(String[] args) throws IOException {
//        FileUtil util = new FileUtil();
//        util.copyTextFile("d:\Backup\document\temp\InputStreamReaderTester.txt", null, "d:\Backup\document\temp\InputStreamReaderTester_bak.txt", "GBK");
//    }
    

}