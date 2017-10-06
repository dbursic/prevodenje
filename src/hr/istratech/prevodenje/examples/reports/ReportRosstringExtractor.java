package hr.istratech.prevodenje.examples.reports;

import hr.istratech.prevodenje.CommandExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by dbursic on 5.10.2017..
 */
public class ReportRosstringExtractor extends CommandExecutor {

    public ReportRosstringExtractor(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    public static String convertToUnicodeString(String hexString) {
        StringBuilder output = new StringBuilder();
        hexString = hexString.replaceAll(" ", "");

        for (int i = 0; i < hexString.length(); i = i + 2) {

            String keyCode = hexString.substring(i, i + 2);

            if (keyCode.equals("00"))
                break;
            if (keyCode.equals("e6"))    //e8
                output.append("æ");
            else if (keyCode.equals("c6"))    //c8
                output.append("Æ");
            else if (keyCode.equals("8e"))
                output.append("Ž");
            else if (keyCode.equals("9e"))
                output.append("ž");
            else if (keyCode.equals("e8"))
                output.append("è");
            else if (keyCode.equals("c8"))
                output.append("È");
            else if (keyCode.equals("d0"))
                output.append("Ð");
            else if (keyCode.equals("f0"))
                output.append("ð");
            else if (keyCode.equals("8a"))
                output.append("Š");
            else if (keyCode.equals("9a"))
                output.append("š");
            else
                output.append((char) Integer.parseInt(keyCode, 16));
        }
        return output.toString();
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

                    String hex = convertToUnicodeString(newLine.trim());

                    System.out.println(hex);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ReportRosstringExtractor reportRosstringExtractor = new ReportRosstringExtractor(new File("D:\\mish_cvs\\misH_moduli"), "PKA");
        reportRosstringExtractor.setReport(true);

        reportRosstringExtractor.execute("rec5010.rex");
    }
}
