import java.util.ArrayList;

//this code does basically what the code in the Main class did for the Stanford data, but for the user inputs.
public class organize {
    ArrayList<ArrayList<String>> paragraph = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<Double>> dataParagraph = new ArrayList<ArrayList<Double>>();
    ArrayList<String> vocabData;
    ArrayList<Double> polarData;

    String[] pathNames;

    DataReader dataReader = new DataReader();

    String s = "";

    public organize(String vocabPath, String polarPath, String data){
        DataReader dataReader = new DataReader();
        vocabData = dataReader.readVocabData(vocabPath);
        polarData = dataReader.readPolarData(polarPath);
        s = data;
        
    }

    public void clean(){
        s = s.replace("~", "");
        s = s.replace("`", "");
        s = s.replace("@", "");
        s = s.replace("#", "");
        s = s.replace("$", "");
        s = s.replace("%", "");
        s = s.replace("^", "");
        s = s.replace("&", "");
        s = s.replace("*", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace("_", "");
        s = s.replace("-", "");
        s = s.replace("+", "");
        s = s.replace("=", "");
        s = s.replace("{", "");
        s = s.replace("[", "");
        s = s.replace("}", "");
        s = s.replace("]", "");
        s = s.replace("|", "");
        //s = s.replace(" \"", "");
        s = s.replace(":", "");
        s = s.replace(";", "");
        //s = s.replace(""", "");
        s = s.replace("<", "");
        s= s.replace("\"", "");
        s = s.replace("'", "");
        s = s.replace(",", "");
        s = s.replace(">", "");
        s = s.replace("/", "");
        s = s.replace("'s", "");
        s = s.replace("s'", "");
        s = s.replace("<br /><br />", " ");
    }

    public void reorganizeData(){
        ArrayList<String> sentence = new ArrayList<String>();
        String word = "";
        int j = 0;
        while(j < s.length()){
            if(s.substring(j, j + 1).equals(".") || s.substring(j, j + 1).equals("!") || s.substring(j, j + 1).equals("?")){
                if(word.contains(".")){
                    word = word.replace(".", "");
                }
                sentence.add(word);
                System.out.println(word + " end");
                word = "";
                paragraph.add((ArrayList<String>) sentence.clone());
                //System.out.println("New sentence #" + z + " paragraph #" + y + sentence);
                sentence.clear();
                j += 2;
            }
            else if(s.substring(j, j + 1).equals(" ")){
                //System.out.println(word);
                if(word.contains(".")){
                    word = word.replace(".", "");
                }
                sentence.add(word);
                System.out.println(word);
                //System.out.println(word);
                word = "";
                //while(j < testNegCleanStringData.get(i).length() && trainPosCleanStringData.get(i).substring(j, j + 1).equals(" ")){
                //j++;
                //}
                j++;
            }
            else{
                word += s.substring(j, j + 1);
                j++;
            }
        }
        //paragraph.clear();
        System.out.println(paragraph);
    }

    public void voacbToPolar(){
        for(int j = 0; j < paragraph.size(); j++){
            ArrayList<Double> dataSentence = new ArrayList<Double>();
            for(int k = 0; k < paragraph.get(j).size(); k++){
                int l = 0;
                if(!paragraph.get(j).get(k).equals(" ")){
                    while(!paragraph.get(j).get(k).equalsIgnoreCase(vocabData.get(l)) && l < vocabData.size() - 1){
                        l++;
                    }
                    dataSentence.add(polarData.get(l));
                }
            }
            dataParagraph.add(dataSentence);
        }
        for(int j = 0; j < dataParagraph.size(); j++){
            int size = dataParagraph.get(j).size();
            if(size <= 40){
                for(int k = 0; k < 40 - size; k++){
                    //System.out.println(orgPolarData.get(i).get(j));
                    //orgPolarData.get(i).get(j).add(0.0);
                }
            }
            else {
                while(dataParagraph.get(j).size() > 40){
                    dataParagraph.get(j).remove(dataParagraph.get(j).size() - 1);
                }
            }
        }
    }

    public ArrayList<ArrayList<Double>> getDataParagraph(){
        return dataParagraph;
    }
}
