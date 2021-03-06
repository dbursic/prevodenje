package hr.istratech.prevodenje;

import hr.istratech.prevodenje.persistence.ConnectionProvider;
import hr.istratech.prevodenje.util.DirectoryUtils;

import java.io.File;

/**
 * Created by dbursic on 3.10.2017..
 */
public abstract class CommandExecutor implements CommandInterface {
    protected File sourcePath;
    protected File targetPath;
    protected String aplikacija;
    protected String formName;
    protected ConnectionProvider connectionProvider;
    protected boolean report = false;

    public CommandExecutor(File sourcePath, String aplikacija) {
        this(sourcePath, null, aplikacija);
    }

    public CommandExecutor(File sourcePath, File targetPath, String aplikacija) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.aplikacija = aplikacija;
    }

    public CommandExecutor(File sourcePath, String aplikacija, ConnectionProvider connectionProvider) {
        this.sourcePath = sourcePath;
        this.aplikacija = aplikacija;
        this.connectionProvider = connectionProvider;
    }

    public void setFormName(String name) {
        this.formName = name;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    @Override
    public void execute() {
        if (formName != null) {
            /*
            * Ako je postavljena specifična forma za obradu
            * */
            execute(formName);
        } else {
            /*
            * Dohvati sve forme iz source direktorija i obradi ih
            * */
            File[] list = report ? DirectoryUtils.getFileListForExtension(sourcePath, "rex") : DirectoryUtils.getFileListForApplication(sourcePath, aplikacija);

            for (int i = 0; i < list.length; i++) {
                File file = list[i];
                execute(file.getName());
            }
        }
    }

    @Override
    public abstract void execute(String name);
}
