import hr.istratech.prevodenje.CommandExecutor;
import hr.istratech.prevodenje.ExampleFactory;
import hr.istratech.prevodenje.ExampleType;
import hr.istratech.prevodenje.persistence.ConnectionProvider;

import java.io.File;

/**
 * Created by dbursic on 2.10.2017..
 */
public class Main {
    public static void main(String[] args) {
        /* Definiranje testnih parametara */
        File sourcePath = new File("D:\\mish_cvs\\misH_moduli");
        sourcePath = new File("D:\\Projekti\\java\\prevodenje2\\resources");
        String aplikacija = "REC";
        File targetPath = new File("D:\\mish_cvs\\misH_moduli\\test");
        ConnectionProvider connectionProvider = new ConnectionProvider("iii-razvoj", "1521", "ORCL", "i3razvoj", "razvoj");

        System.out.println("Pokrenuta testna aplikaicja....");

/*
         FORME
         Korak 1. Dohvatiti sve datoteke u direktoriju za aplikaciju PKA
         Korak 2. Ispiši imena PKA modula
         Korak 3. Izmjeni imena PKA modula u UPPERCASE
         Korak 4. Isprobati ispisati svojstva forme
         Korak 5. Isprobati ispisati kod forme
         Korak 6. Spremiti naslove formi u bazu

*/

/*
         REPORTI
         Korak 1. Generiraj REX
         Korak 2. Dohvati labele u reportima
         Korak 3. Dohvati poruke u reportima
*/

        CommandExecutor example = ExampleFactory.getExample(ExampleType.FORM_NAME_SAVER, aplikacija, sourcePath, targetPath, connectionProvider);
        example.execute();

    }
}
