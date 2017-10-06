package hr.istratech.prevodenje.examples.simple;

import hr.istratech.prevodenje.CommandExecutor;
import hr.istratech.prevodenje.persistence.ConnectionProvider;
import hr.istratech.prevodenje.persistence.OracleService;
import oracle.forms.jdapi.FormModule;
import oracle.forms.jdapi.Jdapi;

import java.io.File;

/**
 * Created by dbursic on 3.10.2017..
 */
public class FormNameSaver extends CommandExecutor {
    OracleService oracleService;

    public FormNameSaver(File sourcePath, String aplikacija, ConnectionProvider connectionProvider) {
        super(sourcePath, aplikacija, connectionProvider);

        oracleService = new OracleService(connectionProvider);
    }

    @Override
    public void execute(String name) {
        // Open the module - this implicitly starts JDAPI
        FormModule formModule = FormModule.open(sourcePath.getPath() + "/" + name);

        String formModuleName = formModule.getName();

        String formModuleNameTitle = formModule.getTitle();

        System.out.println("Datoteka: " + name + "; Forma:" + formModuleName + "; Naslov:" + formModuleNameTitle);

        oracleService.saveModul(formModuleName, formModuleNameTitle);

        // Clean Up
        Jdapi.shutdown();
    }
}