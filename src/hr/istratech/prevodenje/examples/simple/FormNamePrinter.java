package hr.istratech.prevodenje.examples.simple;

import hr.istratech.prevodenje.CommandExecutor;
import oracle.forms.jdapi.FormModule;
import oracle.forms.jdapi.Jdapi;

import java.io.File;

/**
 * Created by dbursic on 3.10.2017..
 */
public class FormNamePrinter extends CommandExecutor {

    public FormNamePrinter(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    @Override
    public void execute(String name) {
        // Open the module - this implicitly starts JDAPI
        FormModule formModule = FormModule.open(sourcePath.getPath() + "/" + name);

        String formModuleName = formModule.getName();

        String formModuleNameTitle = formModule.getTitle();

        System.out.println("Datoteka: " + name + "; Forma:" + formModuleName + "; Naslov:" + formModuleNameTitle);

        // Clean Up
        Jdapi.shutdown();
    }
}
