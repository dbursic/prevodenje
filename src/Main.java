import hr.istratech.prevodenje.examples.FormCodePrinter;
import hr.istratech.prevodenje.examples.FormPropertiesPrinter;
import hr.istratech.prevodenje.examples.simple.FormNameChanger;
import hr.istratech.prevodenje.examples.simple.FormNamePrinter;
import hr.istratech.prevodenje.examples.simple.FormNameSaver;
import hr.istratech.prevodenje.persistence.ConnectionProvider;
import hr.istratech.prevodenje.util.DirectoryUtils;

import java.io.File;

/**
 * Created by dbursic on 2.10.2017..
 */
public class Main {
    public static void main(String[] args) {
        /* Korak 1. */
        System.out.println("Pokrenuta testna aplikaicja....");

        /* Definiranje testnih parametara */
        File sourcePath = new File("D:\\mish_cvs\\misH_moduli");
        String aplikacija = "PKA";

        File[] fileList = DirectoryUtils.getFileListForApplication(sourcePath, aplikacija);

        /* Korak 2. Dohvatiti sve datoteke u direktoriju za aplikaciju PKA */
        for (File file : fileList) {
            System.out.println("Datoteka: " + file.getName());
        }

        /* Korak 3. Ispiši imena PKA modula */
        FormNamePrinter formNamePrinter = new FormNamePrinter(sourcePath, aplikacija);
        for (File file : fileList) {
            formNamePrinter.execute(file.getName());
        }

        /* Definiranje testnih parametara */
        File targetPath = new File("D:\\mish_cvs\\misH_moduli\\test");

        /* Korak 4. Izmjeni imena PKA modula u UPPERCASE */
        FormNameChanger formNameChanger = new FormNameChanger(sourcePath, targetPath, aplikacija);
        for (File file : fileList) {
            formNameChanger.execute(file.getName());
        }

        /* Korak 5. Isprobati ispisati svojstva forme */
        FormPropertiesPrinter formPropertiesPrinter = new FormPropertiesPrinter(sourcePath, aplikacija);
        formPropertiesPrinter.execute("PKA2010.fmb");

        /* Korak 6. Isprobati ispisati kod forme */
        FormCodePrinter formCodePrinter = new FormCodePrinter(sourcePath, aplikacija);
        formCodePrinter.execute("PKA2010.fmb");

        ConnectionProvider connectionProvider = new ConnectionProvider("iii-razvoj", "1521", "ORCL", "i3razvoj", "razvoj");
        /* Korak 7. Spremiti naslove formi u bazu */
        FormNameSaver formNameSaver = new FormNameSaver(sourcePath, aplikacija, connectionProvider);
        formNameSaver.execute("PKA2010.fmb");

    }
}
