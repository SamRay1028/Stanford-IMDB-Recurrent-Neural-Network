import java.util.ArrayList;
import java.util.Random;

//this code defines a review as having two parameters. The first parameter is the numerical data that is representative of the review, and the sentiment of said review (positive or negative).
public class Review {
    public ArrayList<ArrayList<Double>> numericalData;
    public String label;
    public Review(ArrayList<ArrayList<Double>> numericalData, String label){
        this.numericalData = numericalData;
        this.label = label;
    }

    public ArrayList<ArrayList<Double>> getNumericalData(){
        return numericalData;
    }

    public String getLabel(){
        return label;
    }
}
