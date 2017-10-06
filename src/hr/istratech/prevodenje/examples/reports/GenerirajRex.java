package hr.istratech.prevodenje.examples.reports;

import hr.istratech.prevodenje.CommandExecutor;
import hr.istratech.prevodenje.CommandInterface;
import hr.istratech.prevodenje.util.DirectoryUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dbursic on 5.10.2017..
 */
public class GenerirajRex extends CommandExecutor {

    public GenerirajRex(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    @Override
    public void execute() {
        BufferedWriter wrBatFile = null;

        try {
            wrBatFile = new BufferedWriter(new FileWriter(sourcePath.getPath() + "/" + "convertToRex" + aplikacija + ".bat", true));

            for (File file : DirectoryUtils.getFileListForExtension(sourcePath, "rdf")) {
                wrBatFile.write("rwcon60  userid=i3centar/centar@i3centar STYPE=RDFFILE SOURCE=" + file.getName() + " DTYPE=REXFILE DEST=" + file.getName().replace("rdf", "rex") + " batch=YES\n\r");
            }

            wrBatFile.flush();
            wrBatFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void execute(String name) {

    }

    public static void main(String[] args) {
        GenerirajRex generirajRex = new GenerirajRex(new File("D:\\mish_cvs\\misH_moduli"), "PKA");
        generirajRex.execute();
    }

}
