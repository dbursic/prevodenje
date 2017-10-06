package hr.istratech.prevodenje.examples.reports;

import hr.istratech.prevodenje.CommandExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dbursic on 6.10.2017..
 */
public class ReportExtractMessages extends CommandExecutor {
    public ReportExtractMessages(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    public void obradiDioKoda(String fileText, String name) {
        // pra_zmp_zaj.porjez pra_zmp_zaj.poruka
        Pattern patternPraZmpZaj = Pattern.compile("(?u)(?m)(?s)pra_zmp_zaj.por\\w\\w\\w[ \n\r]*\\([ \n\r]*(.+?)[ \n\r]*,[ \n\r]*(.+?)[ \n\r]*[,\\)]");
        Matcher matcherPraZmpZaj = patternPraZmpZaj.matcher(fileText);

        while (matcherPraZmpZaj.find()) {
            String apl = matcherPraZmpZaj.group(1).toUpperCase();
            String sifra = matcherPraZmpZaj.group(2);
            String poruka = matcherPraZmpZaj.group(0);
            System.out.println(apl + " " + sifra + " " + poruka);
        }
    }


    @Override
    public void execute(String name) {
        String thisLine;
        String newLine;

        String fileText = "";

        boolean startDefine = false;
        boolean startCode = false;

        try {
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(new FileInputStream(sourcePath + "\\" + name),
                                    "CP1250"));

            while ((thisLine = br.readLine()) != null) {
                if (thisLine.startsWith("DEFINE  SRW2_QUERY"))
                    startDefine = true;
                if (thisLine.startsWith("DEFINE  TOOL_PLSQL"))
                    startDefine = true;
                if ((thisLine.toUpperCase().contains("<<\"SELECT")) && startDefine)
                    startCode = true;
                if ((thisLine.contains("<<\"function")) && startDefine)
                    startCode = true;
                if ((thisLine.contains("<<\"FUNCTION") || thisLine.contains("<<\"procedure") || thisLine.contains("<<\"PROCEDURE")) && startDefine)
                    startCode = true;

                if (startCode)
                    fileText += thisLine + "\r\n";

                if (thisLine.contains("\">>") && startCode && startDefine) {
                    obradiDioKoda(fileText, name);
                    startDefine = false;
                    startCode = false;
                    fileText = "";
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
