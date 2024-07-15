import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //I used the Stanford IMDB data set to train and test my network. The data set is a collection of 50,000 movie reviews. 25,000 are positive and 25,000 are negative.
        System.out.println("Hello");
        Network network = new Network();
        DataReader dataReader = new DataReader();
        ArrayList<String> vocabData = dataReader.readVocabData("/Users/samraya/Desktop/aclImdb/imdb.vocab");
        ArrayList<Double> polarData = dataReader.readPolarData("/Users/samraya/Desktop/aclImdb/imdbEr.txt");
        ArrayList<String> reviewData = dataReader.readVocabData("Macintosh HD/Users/samraya/Desktop/aclImdb/train/neg/1_1.txt");
        String[] pathNamesTestNeg = dataReader.pathNames("/Users/samraya/Desktop/aclImdb/test/neg");
        String[] pathNamesTestPos = dataReader.pathNames("/Users/samraya/Desktop/aclImdb/test/pos");
        String[] pathNamesTrainNeg = dataReader.pathNames("/Users/samraya/Desktop/aclImdb/train/neg");
        String[] pathNamesTrainPos = dataReader.pathNames("/Users/samraya/Desktop/aclImdb/train/Pos");
        ArrayList<Review> testReviews = new ArrayList<Review>();
        ArrayList<Review> trainReviews = new ArrayList<Review>();
        ArrayList<String> testNegCleanStringData = new ArrayList<String>();
        ArrayList<String> testPosCleanStringData = new ArrayList<String>();
        ArrayList<String> trainNegCleanStringData = new ArrayList<String>();
        ArrayList<String> trainPosCleanStringData = new ArrayList<String>();
        ArrayList<ArrayList<ArrayList<String>>> orgTestNeg = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> orgTestPos = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> orgTrainNeg = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> orgTrainPos = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> orgData = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<Double>>> orgPolarData = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<ArrayList<ArrayList<Double>>> finalPolarData = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<ArrayList<String>> paragraph = new ArrayList<ArrayList<String>>();
        ArrayList<String> sentence = new ArrayList<String>();
        ArrayList<Review> testData = new ArrayList<Review>();
        ArrayList<Review> trainData = new ArrayList<Review>();
        ArrayList<Review> holder = new ArrayList<Review>();
        ArrayList<ArrayList<Review>> trainDataBatches = new ArrayList<ArrayList<Review>>();
        int miniBatchSize = 100;
        int epoch = 0;
        int size = 0;
        int maxSentenceLength = 39;
        int sentenceNumber = 0;
        int totalNumOfSentences = 0;
        int count = 0;
        int right = 0;
        int wrong = 0;
        double learningRate = 0.001;
        double decayRate = 0.0;
        double regularizationRate = 0.0;
        double AvgCostofEpoch = 0.0;
        double currentLowestAvgCost = 1000000;
        double beta = 0.99;
        String word = "";
        String s = "";
        Character c = ' ';
        boolean exists = false;
        for(int i  = 0; i < vocabData.size(); i++){
            System.out.println(vocabData.get(i));
        }
        for(int i  = 0; i < polarData.size(); i++){
            System.out.println(polarData.get(i));
        }
        for(int i  = 0; i < reviewData.size(); i++){
            System.out.println(reviewData.get(i));
        }
        //This part of the code "cleans" the data, basically gets rid of anything that isn't words and end punctuation.
        for(int i = 0; i < pathNamesTestNeg.length; i++){
            s = dataReader.fileToString("/Users/samraya/Desktop/aclImdb/test/neg/" + pathNamesTestNeg[i]);
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

            s = s.replace(":", "");
            s = s.replace(";", "");

            s = s.replace("<", "");
            s= s.replace("\"", "");
            s = s.replace("'", "");
            s = s.replace(",", "");
            s = s.replace(">", "");
            s = s.replace("/", "");
            s = s.replace("'s", "");
            s = s.replace("s'", "");
            s = s.replace("<br /><br />", " ");
            testNegCleanStringData.add(s + " ");
        }
        for(int i = 0; i < pathNamesTestPos.length; i++){
            s = dataReader.fileToString("/Users/samraya/Desktop/aclImdb/test/pos/" + pathNamesTestPos[i]);
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
            s = s.replace(":", "");
            s = s.replace(";", "");
            s = s.replace("<", "");
            s= s.replace("\"", "");
            s = s.replace("'", "");
            s = s.replace(",", "");
            s = s.replace(">", "");
            s = s.replace("/", "");
            s = s.replace("'s", "");
            s = s.replace("s'", "");
            s = s.replace("<br /><br />", " ");
            testPosCleanStringData.add(s + " ");
        }
        for(int i = 0; i < pathNamesTrainNeg.length; i++){
            s = dataReader.fileToString("/Users/samraya/Desktop/aclImdb/train/neg/" + pathNamesTrainNeg[i]);
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
            s = s.replace(":", "");
            s = s.replace(";", "");
            s = s.replace("<", "");
            s= s.replace("\"", "");
            s = s.replace("'", "");
            s = s.replace(",", "");
            s = s.replace(">", "");
            s = s.replace("/", "");
            s = s.replace("'s", "");
            s = s.replace("s'", "");
            s = s.replace("<br /><br />", " ");
            trainNegCleanStringData.add(s + " ");
        }
        for(int i = 0; i < pathNamesTrainPos.length; i++){
            s = dataReader.fileToString("/Users/samraya/Desktop/aclImdb/train/pos/" + pathNamesTrainPos[i]);
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
            s = s.replace(":", "");
            s = s.replace(";", "");
            s = s.replace("<", "");
            s = s.replace("\"", "");
            s = s.replace("'", "");
            s = s.replace(",", "");
            s = s.replace(">", "");
            s = s.replace("/", "");
            s = s.replace("'s", "");
            s = s.replace("s'", "");
            s = s.replace("<br /><br />", " ");
            trainPosCleanStringData.add(s + " ");
        }

        //this part of the code reorganizes the data into lists. Individual words are arranged into lists known as sentences, and sentences are arranged into lists known as paragraphs. Reorganizing the data into lists makes the words more easily accessible by future applications in the program.
        for(int i = 0; i < testNegCleanStringData.size(); i++){
            word = "";
            int j = 0;
            while(j < testNegCleanStringData.get(i).length()){
                if(testNegCleanStringData.get(i).substring(j, j + 1).equals(".") || testNegCleanStringData.get(i).substring(j, j + 1).equals("!") || testNegCleanStringData.get(i).substring(j, j + 1).equals("?")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    paragraph.add((ArrayList<String>) sentence.clone());
                    sentence.clear();
                    j += 2;
                }
                else if(testNegCleanStringData.get(i).substring(j, j + 1).equals(" ")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    j++;
                }
                else{
                    word += testNegCleanStringData.get(i).substring(j, j + 1);
                    j++;
                }
            }
            orgTestNeg.add((ArrayList<ArrayList<String>>) paragraph.clone());
            paragraph.clear();
        }
        for(int i = 0; i < testPosCleanStringData.size(); i++){
            word = "";
            int j = 0;
            while(j < testPosCleanStringData.get(i).length()){
                if(testPosCleanStringData.get(i).substring(j, j + 1).equals(".") || testPosCleanStringData.get(i).substring(j, j + 1).equals("!") || testPosCleanStringData.get(i).substring(j, j + 1).equals("?")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    paragraph.add((ArrayList<String>) sentence.clone());
                    sentence.clear();
                    j += 2;
                }
                else if(testPosCleanStringData.get(i).substring(j, j + 1).equals(" ")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    j++;
                }
                else{
                    word += testPosCleanStringData.get(i).substring(j, j + 1);
                    j++;
                }
            }
            orgTestPos.add((ArrayList<ArrayList<String>>) paragraph.clone());
            paragraph.clear();
        }
        for(int i = 0; i < trainNegCleanStringData.size(); i++){
            word = "";
            int j = 0;
            while(j < trainNegCleanStringData.get(i).length()){
                if(trainNegCleanStringData.get(i).substring(j, j + 1).equals(".") || trainNegCleanStringData.get(i).substring(j, j + 1).equals("!") || trainNegCleanStringData.get(i).substring(j, j + 1).equals("?")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    paragraph.add((ArrayList<String>) sentence.clone());
                    sentence.clear();
                    j += 2;
                }
                else if(trainNegCleanStringData.get(i).substring(j, j + 1).equals(" ")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    j++;
                }
                else{
                    word += trainNegCleanStringData.get(i).substring(j, j + 1);
                    j++;
                }
            }
            orgTrainNeg.add((ArrayList<ArrayList<String>>) paragraph.clone());
            paragraph.clear();
        }
        for(int i = 0; i < trainPosCleanStringData.size(); i++){
            word = "";
            int j = 0;
            while(j < trainPosCleanStringData.get(i).length()){
                if(trainPosCleanStringData.get(i).substring(j, j + 1).equals(".") || trainPosCleanStringData.get(i).substring(j, j + 1).equals("!") || trainPosCleanStringData.get(i).substring(j, j + 1).equals("?")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    paragraph.add((ArrayList<String>) sentence.clone());
                    sentence.clear();
                    j += 2;
                }
                else if(trainPosCleanStringData.get(i).substring(j, j + 1).equals(" ")){
                    if(word.contains(".")){
                        word = word.replace(".", "");
                    }
                    sentence.add(word);
                    word = "";
                    j++;
                }
                else{
                    word += trainPosCleanStringData.get(i).substring(j, j + 1);
                    j++;
                }
            }
            orgTrainPos.add((ArrayList<ArrayList<String>>) paragraph.clone());
            paragraph.clear();
        }
        orgData.addAll(orgTestNeg);
        orgData.addAll(orgTestPos);
        orgData.addAll(orgTrainNeg);
        orgData.addAll(orgTrainPos);

        //This part of the code replaces the words with their respective polar value, which is essentially a numerical value that the computer can understand.
        for(int i = 0; i < orgData.size(); i++){
            ArrayList<ArrayList<Double>> dataParagraph = new ArrayList<ArrayList<Double>>();
            for(int j = 0; j < orgData.get(i).size(); j++){
                ArrayList<Double> dataSentence = new ArrayList<Double>();
                for(int k = 0; k < orgData.get(i).get(j).size(); k++){
                    int l = 0;
                    if(!orgData.get(i).get(j).get(k).equals(" ")){
                        while(!orgData.get(i).get(j).get(k).equalsIgnoreCase(vocabData.get(l)) && l < vocabData.size() - 1){
                            l++;
                        }
                        dataSentence.add(polarData.get(l));
                    }
                }
                dataParagraph.add(dataSentence);
            }
            orgPolarData.add(dataParagraph);
        }
        for(int i = 0; i < orgPolarData.size(); i++){
            for(int j = 0; j < orgPolarData.get(i).size(); j++){
                size = orgPolarData.get(i).get(j).size();
                if(size <= 40){
                    for(int k = 0; k < 40 - size; k++){
                        //System.out.println(orgPolarData.get(i).get(j));
                        //orgPolarData.get(i).get(j).add(0.0);
                    }
                }
                else {
                    while(orgPolarData.get(i).get(j).size() > 40){
                        orgPolarData.get(i).get(j).remove(orgPolarData.get(i).get(j).size() - 1);
                    }
                }
            }
        }
        count = 0;
        for(int i = 0; i < 12500; i++){
            testData.add(new Review(orgPolarData.get(i), "neg"));
        }
        for(int i = 12500; i < 25000; i++){
            testData.add(new Review(orgPolarData.get(i), "pos"));
        }
        for(int i = 25000; i < 37500; i++){
            trainData.add(new Review(orgPolarData.get(i), "neg"));
        }
        for(int i = 37500; i < 50000; i++){
            trainData.add(new Review(orgPolarData.get(i), "pos"));
        }
        Collections.shuffle(testData);

        //this part of the code trains the network.
        while (epoch < 80){
            count = 0;
            Collections.shuffle(trainData);
            for(int i = 0; i < trainData.size() / miniBatchSize; i++){
                holder = new ArrayList<Review>();
                for(int j = 0; j < miniBatchSize; j++){
                    holder.add(trainData.get(i * miniBatchSize + j));
                }
                trainDataBatches.add((ArrayList<Review>) holder.clone());
            }
            for(int i = 0; i < trainDataBatches.size(); i++){
                network.setCost(0.0);
                for(int j = 0; j < trainDataBatches.get(i).size(); j++){
                    for(int k = 0; k < trainDataBatches.get(i).get(j).getNumericalData().size(); k++){
                        network.feedForward(trainDataBatches.get(i).get(j).getNumericalData().get(k));
                        network.backPropagation2(trainDataBatches.get(i).get(j).getLabel(), beta);

                    }
                    network.calculateCost(trainDataBatches.get(i).get(j).getLabel());
                    network.clearPHVal();
                }
                network.clearPHVal();
                System.out.println("aVals and hVals and phVals: ");
                System.out.println(network.toString2());
                System.out.println();
                System.out.println();
                network.subtract(learningRate, regularizationRate);
                AvgCostofEpoch += network.getCost();
                count++;
                System.out.println("Batch Index Number: " + count);
                System.out.println("Cost: " + network.getCost());
                System.out.println("Lowest Cost of Current Epoch: " + AvgCostofEpoch);
                System.out.println("Lowest Average Cost throughout training: " + currentLowestAvgCost);
                System.out.println("Learning Rate: " + learningRate);
                System.out.println("Epoch: " + epoch);
                System.out.println("Sentiment: " + trainDataBatches.get(i).get(99).getLabel());
                System.out.println();
                System.out.println();
            }
            AvgCostofEpoch /= trainDataBatches.size();
            if(AvgCostofEpoch > currentLowestAvgCost && learningRate > 0.0){
                learningRate -= decayRate;
            }
            else{
                currentLowestAvgCost = AvgCostofEpoch;
            }
            AvgCostofEpoch = 0.0;
            trainDataBatches.clear();
            epoch++;
        }
        System.out.println("Size: " + testData.size());

        //this part of the code tests the network to determine its accuracy.
        for(int i = 0; i < testData.size(); i++){
            for(int j = 0; j < testData.get(i).getNumericalData().size(); j++){
                network.feedForward(testData.get(i).getNumericalData().get(j));
                network.setPHVal();
            }
            network.clearPHVal();
            if(network.compare(testData.get(i).getLabel())){
                right++;
                System.out.println("right");
            }
            else{
                wrong++;
                System.out.println("wrong");
            }
        }
        System.out.println(right);
        System.out.println(wrong);

        //this part of the code allows for users to make inputs of their own to test the network with.
        while(0 < 1){
            System.out.println("Input a sentence or a word for sentiment analysis");
            Scanner myObj = new Scanner(System.in);
            String text = myObj.nextLine();
            if(text.equalsIgnoreCase("stop")){
                break;
            }
            organize organizer = new organize("/Users/samraya/Desktop/aclImdb/imdb.vocab", "/Users/samraya/Desktop/aclImdb/imdbEr.txt", text);
            organizer.clean();
            organizer.reorganizeData();
            organizer.voacbToPolar();
            ArrayList<ArrayList<Double>> inputData = organizer.getDataParagraph();
            System.out.println("Size: " + organizer.getDataParagraph().size());
            for(int j = 0; j < inputData.size(); j++){
                for(int k = 0; k < inputData.get(j).size(); k++){
                    System.out.print(inputData.get(j).get(k));
                }
                System.out.println();
                network.feedForward(inputData.get(j));
                network.setPHVal();
            }
            network.clearPHVal();
            System.out.println(network.predict());
        }
    }
}