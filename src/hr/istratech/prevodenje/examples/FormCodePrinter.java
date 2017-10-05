package hr.istratech.prevodenje.examples;

import hr.istratech.prevodenje.FormCommandExecutor;
import oracle.forms.jdapi.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dbursic on 3.10.2017..
 */
public class FormCodePrinter extends FormCommandExecutor {
    public FormCodePrinter(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    @Override
    public void execute(String name) {
        FormModule formModule = FormModule.open(sourcePath.getPath() + "/" + name);

        System.out.println("Forma " + name + ": " + formModule.getTitle());

        printProgramUnits(formModule);

        printTriggers(formModule);

        Jdapi.shutdown();

    }

    private void printTriggers(FormModule formModule) {
        printFormLevelTriggers(formModule);

        printBlockLevelTriggers(formModule);

        printItemLevelTriggers(formModule);
    }

    private void printProgramUnitNameAndText(ProgramUnit progUnit, String name) {
        System.out.println("Program Unit: " + progUnit.getName());
    }

    private void printTriggerNameAndText(Trigger trigger, String name) {
        System.out.println("Trigger: " + trigger.getName());
    }

    private void printItemLevelTriggers(FormModule formModule) {
        JdapiIterator blocks = formModule.getBlocks();  //  To navigate to all the Blocks

        while (blocks.hasNext()) {
            Block block = (Block) blocks.next();
            JdapiIterator items = block.getItems();

            while (items.hasNext()) {
                Item item = (Item) items.next();
                JdapiIterator itemLevelTriggers = item.getTriggers();

                while (itemLevelTriggers.hasNext()) {
                    Trigger itemLevelTrigger = (Trigger) itemLevelTriggers.next();
                    printTriggerNameAndText(itemLevelTrigger, formModule.getName());
                }
            }
        }
    }

    private void printBlockLevelTriggers(FormModule formModule) {
        JdapiIterator blocks = formModule.getBlocks();  //  To navigate to all the Blocks

        while (blocks.hasNext()) {
            Block block = (Block) blocks.next();
            JdapiIterator blockLevelTriggers = block.getTriggers();   // To extract block Level Triggers

            while (blockLevelTriggers.hasNext()) {
                Trigger blockLevelTrigger = (Trigger) blockLevelTriggers.next();
                printTriggerNameAndText(blockLevelTrigger, formModule.getName());
            }
        }
    }

    private void printFormLevelTriggers(FormModule formModule) {
        JdapiIterator proc = formModule.getTriggers();   // To extract Form Level Triggers
        while (proc.hasNext()) {
            Trigger formLevelTrigger = (Trigger) proc.next();
            printTriggerNameAndText(formLevelTrigger, formModule.getName());
        }
    }

    private void printProgramUnits(FormModule formModule) {
        JdapiIterator progUnits = formModule.getProgramUnits();
        while (progUnits.hasNext()) {
            ProgramUnit progUnit = (ProgramUnit) progUnits.next();
            printProgramUnitNameAndText(progUnit, formModule.getName());
        }

    }



}
