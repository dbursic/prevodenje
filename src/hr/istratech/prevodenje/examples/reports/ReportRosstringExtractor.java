package hr.istratech.prevodenje.examples.reports;

import hr.istratech.prevodenje.CommandExecutor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

/**
 * Created by dbursic on 5.10.2017..
 */
public class ReportRosstringExtractor extends CommandExecutor {

    public ReportRosstringExtractor(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    /*
        input : "æÆŽžèÈÐðŠš";
        output : "e6c68e9ee8c8d0f08a9a";
    */
    public String toHexString(byte[] ba) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ba.length; i++)
            str.append(String.format("%x", ba[i]));
        return str.toString();
    }

    /*
        input : "e6c68e9ee8c8d0f08a9a";
        output : "æÆŽžèÈÐðŠš";
    */
    public String convertToStringFromHexadecimal(String hexString) throws CharacterCodingException {
        String hex = hexString.replaceAll(" ", "");
        byte byteArray[] = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length(); i += 2) {
            String substring = hex.substring(i, i + 2);
            byteArray[i / 2] = new Integer(Integer.parseInt(substring, 16)).byteValue();
        }

        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        return decoder.decode(buffer).toString();
    }


    @Override
    public void execute(String name) {
        String thisLine;
        String newLine;
        try {
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(new FileInputStream(sourcePath + "\\" + name),
                                    "CP1250"));

            while ((thisLine = br.readLine()) != null) {
                if (thisLine.startsWith("DEFINE  ROSSTRINGS")) {
/*
                        DEFINE  ROSSTRINGS
                        BEGIN
                           groupid = 2237
                           stringid = 0
                           lfid = 0
                           cs = 170
                           len = 7
                           str = (BINARY)
                        <<"
                        42727574 6f0a0000
                        ">>
                        END
*/
                    while ((newLine = br.readLine()) != null) {
                        if (newLine.startsWith("<<\"")) break;
                    }

                    newLine = br.readLine();
                    String pom = br.readLine();
                    while (!pom.contains(">>")) {
                        newLine += pom;
                        pom = br.readLine();
                    }

                    String hex = convertToStringFromHexadecimal(newLine.trim());

                    System.out.println("Report: " + name + " Labela: " + hex);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        ReportRosstringExtractor reportRosstringExtractor = new ReportRosstringExtractor(new File("D:\\mish_cvs\\misH_moduli"), "PKA");
        reportRosstringExtractor.execute("rec5010.rex");

    }
}
