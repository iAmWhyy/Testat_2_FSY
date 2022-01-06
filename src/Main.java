import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        File destDirectory = handleFolderChooser();
        deleteFolder(destDirectory);
        destDirectory.mkdirs();
        StringBuilder textForInfoFile = new StringBuilder("Reihenfolge, der in den RBT eingefügten Zahlen:\n");
        RBT rbt = new RBT();
        int numberOfInsertions = 15;
        for (int i = 0; i < numberOfInsertions; i++) {
            int rndNum = getRndNumBetween1And100();
            System.out.print(rndNum + ((i<numberOfInsertions-1)?", ":""));
            rbt.insert(rndNum);
            String rbtInDotFormat = rbt.printDotGraph();
            writeToDotFile(destDirectory.getAbsolutePath(), rbtInDotFormat, i+1);
            textForInfoFile.append(String.format("%02d", i + 1)).append(". ").append(String.format("%3d", rndNum)).append("\n");
        }
        writeToInfoTxt(textForInfoFile.toString(), destDirectory.getAbsolutePath());
    }

    private static void writeToInfoTxt(String textForInfoFile, String pathOfDestDir) {
        try(PrintWriter out = new PrintWriter(
                pathOfDestDir + "\\info.txt")){
            out.write(textForInfoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static File handleFolderChooser() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        folderChooser.setDialogTitle("Wähle Pfad, in dem der Ordner für die Ausgabedateien erstellt wird");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = folderChooser.showOpenDialog(folderChooser);
        String pathOfDestinationFolder;
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = folderChooser.getSelectedFile();
            pathOfDestinationFolder = selectedFile.getAbsolutePath();
        }
        else{
            pathOfDestinationFolder = System.getProperty("user.home");
        }
        pathOfDestinationFolder += "\\FilesForRBT";
        System.out.println(pathOfDestinationFolder);
        return new File(pathOfDestinationFolder);
    }

    private static void writeToDotFile(String dirname, String str, int step) {
        try(PrintWriter out = new PrintWriter(
                dirname + "\\RBTree_" + String.format("%02d", step) + ".dot")){
            out.write(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int getRndNumBetween1And100(){
        Random rnd = new Random();
        return rnd.nextInt(1,101);
    }

    //https://stackoverflow.com/a/7768086/15606459
    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
