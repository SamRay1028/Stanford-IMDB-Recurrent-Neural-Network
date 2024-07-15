import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

//this class essentially imports all of the necessary datat into the code.
public class DataReader {
    public DataReader(){

    }

    public ArrayList<String> readVocabData(String path){
        ArrayList<String> vocabData = new ArrayList<String>();
        try(BufferedReader dataReader = new BufferedReader(new FileReader(path))){
            String line;
            while((line = dataReader.readLine()) != null){
                vocabData.add(line);
            }
        }
        catch(Exception e){

        }
        return vocabData;
    }

    public ArrayList<Double> readPolarData(String path){
        ArrayList<Double> polarData = new ArrayList<Double>();
        try(BufferedReader dataReader = new BufferedReader(new FileReader(path))){
            String line;
            while((line = dataReader.readLine()) != null){
                polarData.add(Double.parseDouble(line));
            }
        }
        catch(Exception e){

        }
        return polarData;

    }

    public ArrayList<String> readReviewData(String label, String path){
        ArrayList<String> reviewData = new ArrayList<String>();
        try(BufferedReader dataReader = new BufferedReader(new FileReader(path))){
            while(dataReader.lines() != null){
                reviewData.add(dataReader.readLine());
            }
        }
        catch(Exception e){

        }
        return reviewData;
    }

    public String[] pathNames(String path){
        File file = new File(path);
        String[] pathNames = file.list();
        return pathNames;
    }

    public String fileToString(String path){
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while((line = reader.readLine()) != null){
                content.append(line).append("\n");
            }
        }
        catch(IOException e){
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return content.toString();
    }
}
