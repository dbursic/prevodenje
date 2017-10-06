package hr.istratech.prevodenje.examples.simple;

import hr.istratech.prevodenje.CommandExecutor;
import oracle.forms.jdapi.FormModule;
import oracle.forms.jdapi.Jdapi;

import java.io.File;

/**
 * Created by dbursic on 3.10.2017..
 */
public class FormNameChanger extends CommandExecutor {

    public FormNameChanger(File sourcePath, File targetPath, String aplikacija) {
        super(sourcePath, targetPath, aplikacija);
    }

    @Override
    public void execute(String name) {
        FormModule formModule = FormModule.open(sourcePath.getPath() + "/" + name);

        formModule.setTitle(formModule.getTitle().toUpperCase());

        formModule.save(targetPath.getPath() + "/" + name);

        System.out.println("Obraðen: " + name);

        Jdapi.shutdown();
    }
}
