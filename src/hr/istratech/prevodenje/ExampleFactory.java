package hr.istratech.prevodenje;

import hr.istratech.prevodenje.examples.FormCodePrinter;
import hr.istratech.prevodenje.examples.FormPropertiesPrinter;
import hr.istratech.prevodenje.examples.reports.GenerirajRex;
import hr.istratech.prevodenje.examples.reports.ReportRosstringExtractor;
import hr.istratech.prevodenje.examples.simple.FormNameChanger;
import hr.istratech.prevodenje.examples.simple.FormNamePrinter;
import hr.istratech.prevodenje.examples.simple.FormNameSaver;
import hr.istratech.prevodenje.persistence.ConnectionProvider;

import java.io.File;

/**
 * Created by dbursic on 5.10.2017..
 */
public class ExampleFactory {

    public static CommandExecutor getExample(ExampleType exampleType, String aplikacija, File sourcePath, File targetPath, ConnectionProvider connectionProvider) {
        if (exampleType.equals(ExampleType.FORM_NAME_PRINTER))
            return getFormNamesPrinterExample(sourcePath, aplikacija);
        if (exampleType.equals(ExampleType.FORM_NAME_CHANGER))
            return getFormNamesChangerExample(sourcePath, aplikacija, targetPath);
        if (exampleType.equals(ExampleType.FORM_NAME_SAVER))
            return getFormNamesSaverExample(sourcePath, aplikacija, connectionProvider);
        if (exampleType.equals(ExampleType.FORM_CODE_PRINTER))
            return getFormCodePrinterExample(sourcePath, aplikacija);
        if (exampleType.equals(ExampleType.FORM_PROPERTIES_PRINTER))
            return getFormPropertiesPrinterExample(sourcePath, aplikacija);
        if (exampleType.equals(ExampleType.REPORT_REX_GENERATOR))
            return getReportRexGeneratorExample(sourcePath, aplikacija);
        if (exampleType.equals(ExampleType.REPORT_LABEL_EXTRACTOR))
            return getReportLabelExtractorExample(sourcePath, aplikacija);

        return null;
    }

    private static CommandExecutor getReportLabelExtractorExample(File sourcePath, String aplikacija) {
        GenerirajRex generirajRex = new GenerirajRex(sourcePath, aplikacija);
        generirajRex.setReport(true);
        return generirajRex;
    }

    private static CommandExecutor getReportRexGeneratorExample(File sourcePath, String aplikacija) {
        ReportRosstringExtractor reportRosstringExtractor = new ReportRosstringExtractor(sourcePath, aplikacija);
        reportRosstringExtractor.setReport(true);
        reportRosstringExtractor.setFormName("pka5010.rex");
        return reportRosstringExtractor;
    }

    private static CommandExecutor getFormPropertiesPrinterExample(File sourcePath, String aplikacija) {
        return new FormPropertiesPrinter(sourcePath, aplikacija);
    }

    private static CommandExecutor getFormCodePrinterExample(File sourcePath, String aplikacija) {
        return new FormCodePrinter(sourcePath, aplikacija);
    }

    private static CommandExecutor getFormNamesSaverExample(File sourcePath, String aplikacija, ConnectionProvider connectionProvider) {
        return new FormNameSaver(sourcePath, aplikacija, connectionProvider);
    }

    private static CommandExecutor getFormNamesChangerExample(File sourcePath, String aplikacija, File targetPath) {
        return new FormNameChanger(sourcePath, targetPath, aplikacija);
    }

    private static CommandExecutor getFormNamesPrinterExample(File sourcePath, String aplikacija) {
        return new FormNamePrinter(sourcePath, aplikacija);
    }

}
