package hr.istratech.prevodenje.examples;

import hr.istratech.prevodenje.CommandExecutor;
import oracle.forms.jdapi.*;

import java.io.File;

/**
 * Created by dbursic on 3.10.2017..
 */
public class FormPropertiesPrinter extends CommandExecutor {

    public FormPropertiesPrinter(File sourcePath, String aplikacija) {
        super(sourcePath, aplikacija);
    }

    @Override
    public void execute(String name) {
        FormModule formModule = FormModule.open(sourcePath.getPath() + "/" + name);

        System.out.println("Forma " + name + ": " + formModule.getTitle());

        printAlerts(formModule);

        printWindows(formModule);

        printCanvas(formModule);

        printBlocksItems(formModule);

        printRecordGroups(formModule);

        printLOVs(formModule);

        printVisualAttributes(formModule);

        Jdapi.shutdown();

    }

    private void printVisualAttributes(FormModule formModule) {
        JdapiIterator visualAttributes = formModule.getVisualAttributes();
        while (visualAttributes.hasNext()) {
            VisualAttribute visualAttribute = (VisualAttribute) visualAttributes.next();
            System.out.println("VisualAttribute: " + visualAttribute.getName());
        }
    }

    private void printRecordGroups(FormModule formModule) {
        JdapiIterator recordGroups = formModule.getRecordGroups();
        while (recordGroups.hasNext()) {
            RecordGroup recordGroup = (RecordGroup) recordGroups.next();
            System.out.println("RecordGroup: " + recordGroup.getName());
        }
    }

    private void printLOVs(FormModule formModule) {
        JdapiIterator LOVs = formModule.getLOVs();
        while (LOVs.hasNext()) {
            LOV LOV = (LOV) LOVs.next();
            RecordGroup rg = LOV.getRecordGroupObject();
            System.out.println("LOV: " + LOV.getName() + "  " + LOV.getRecordGroupObject().getName());
        }
    }

    private void printBlocksItems(FormModule formModule) {
        JdapiIterator blocks = formModule.getBlocks();  //  To navigate to all the Blocks
        while (blocks.hasNext()) {
            Block block = (Block) blocks.next();
            System.out.println("Block: " + block.getName());
            JdapiIterator items = block.getItems();

            while (items.hasNext()) {
                Item item = (Item) items.next();
                if (item.getName().endsWith("REZ_RBR")) {
                    System.out.println("BC Value: " + item.getBackColor());

                    System.out.println("Enabled: " + item.getPropertyState(JdapiTypes.ENABLED_PTID));
                    System.out.println("X Position: " + item.getPropertyState(JdapiTypes.X_POSITION_PTID));
                    System.out.println("Background color: " + item.getPropertyState(JdapiTypes.BACK_COLOR_PTID));
                    System.out.println("Width: " + item.getPropertyState(JdapiTypes.WIDTH_PTID));
                    System.out.println("Height: " + item.getPropertyState(JdapiTypes.HEIGHT_PTID));
                    System.out.println("BC Value After: " + item.getBackColor());
                    System.out.println("Background color After: " + item.getPropertyState(JdapiTypes.BACK_COLOR_PTID));

                }
            }
        }
    }

    private void printCanvas(FormModule formModule) {
        JdapiIterator canvases = formModule.getCanvases();
        while (canvases.hasNext()) {
            Canvas canvas = (Canvas) canvases.next();
            System.out.println("Canvas: " + canvas.getName());

            JdapiIterator graphicses = canvas.getGraphicses();
            while (graphicses.hasNext()) {
                Graphics graphics = (Graphics) graphicses.next();
                System.out.println("Graphics: " + graphics.getName());
                printGraphicesRecursive(graphics);
            }
            JdapiIterator tabPages = canvas.getTabPages();
            while (tabPages.hasNext()) {
                TabPage tabPage = (TabPage) tabPages.next();
                System.out.println("TabPage: " + tabPage.getName());
            }
        }
    }

    private void printGraphicesRecursive(Graphics graphicsParent) {
        JdapiIterator graphicses = graphicsParent.getGraphicses();
        while (graphicses.hasNext()) {
            Graphics graphics = (Graphics) graphicses.next();
            System.out.println("Graphics: " + graphics.getName());
            printGraphicesRecursive(graphics);
        }
    }

    private void printWindows(FormModule formModule) {
        JdapiIterator windows = formModule.getWindows();

        while (windows.hasNext()) {
            Window window = (Window) windows.next();
            System.out.println("Window: " + window.getName() + " " + window.getTitle());
        }
    }

    private void printAlerts(FormModule formModule) {
        JdapiIterator alerts = formModule.getAlerts();
        while (alerts.hasNext()) {
            Alert alert = (Alert) alerts.next();
            System.out.println("Alert: " + alert.getName() + " " + alert.getTitle());
        }
    }

}
