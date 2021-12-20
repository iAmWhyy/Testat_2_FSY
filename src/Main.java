import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        String dirname = "C:\\DotFilesOfRBT";
        File directory = new File(dirname);
        deleteFolder(directory);
        directory.mkdirs();
        RBT rbt = new RBT();
        int numberOfInsertions = 15;
        for (int i = 0; i < numberOfInsertions; i++) {
            int rndNum = getRndNumBetween1And100();
            System.out.print(rndNum + ((i<numberOfInsertions-1)?", ":""));
            rbt.insert(rndNum);
            String str = rbt.printDotGraph();
            createOutputStream(dirname, str, i+1);
        }
    }

    private static void createOutputStream(String dirname, String str, int step) {
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
