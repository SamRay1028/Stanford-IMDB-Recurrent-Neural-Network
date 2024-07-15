import java.util.ArrayList;
import java.util.Random;

public class Network {
    int numLayers = 3;
    int numSentences = 10;
    double c = 10000;
    double regularization = 0;
    double Tc = 0.0;
    Random rand = new Random();
    ArrayList<Integer> layers = new ArrayList<Integer>();
    ArrayList<double[]> hVal = new ArrayList<double[]>();
    ArrayList<double[]> phVal = new ArrayList<double[]>();
    ArrayList<double[]> aVal = new ArrayList<double[]>();
    ArrayList<double[][]> wx = new ArrayList<double[][]>();
    ArrayList<double[][]> wh = new ArrayList<double[][]>();
    ArrayList<double[]> biases = new ArrayList<double[]>();
    ArrayList<double[][]> dwx = new ArrayList<double[][]>();
    ArrayList<double[][]> dwh = new ArrayList<double[][]>();
    ArrayList<double[]> db = new ArrayList<double[]>();
    ArrayList<double[][]> dwxSoS = new ArrayList<double[][]>();
    ArrayList<double[][]> dwhSoS = new ArrayList<double[][]>();
    ArrayList<double[]> dbSoS = new ArrayList<double[]>();
    ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();

    //Below, sets up an instance of the Network class, this includes seting up the weights, biases, and neurons that make up the neural network's structure
    public Network() {
        layers.add(40);
        layers.add(100);
        layers.add(2);
        double[] h = new double[0];
        for (int i = 0; i < numLayers; i++) {
            h = new double[layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                h[j] = 0.0;
            }
            hVal.add(h);
        }
        double[] ph = new double[0];
        for (int i = 1; i < numLayers - 1; i++) {
            ph = new double[layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                ph[j] = 0.0;
            }
            phVal.add(ph);
        }
        double[] a = new double[0];
        for (int i = 0; i < numLayers; i++) {
            a = new double[layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                a[j] = 0.0;
            }
            aVal.add(a);
        }
        double[][] w = new double[0][0];
        for (int i = 0; i < numLayers - 1; i++) {
            w = new double[layers.get(i + 1)][layers.get(i)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    w[j][k] = rand.nextGaussian();
                }
            }
            wx.add(w);
        }
        for (int i = 1; i < numLayers - 1; i++) {
            w = new double[layers.get(i)][layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    w[j][k] = rand.nextGaussian();
                }
            }
            wh.add(w);
        }
        double[] b = new double[0];
        for (int i = 0; i < numLayers - 1; i++) {
            b = new double[layers.get(i + 1)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                b[j] = rand.nextGaussian();
            }
            biases.add(b);
        }
        for (int i = 0; i < numLayers - 1; i++) {
            w = new double[layers.get(i + 1)][layers.get(i)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    w[j][k] = 0.0;
                }
            }
            dwx.add(w);
        }
        for (int i = 1; i < numLayers - 1; i++) {
            w = new double[layers.get(i)][layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    w[j][k] = 0.0;
                }
            }
            dwh.add(w);
        }
        for (int i = 0; i < numLayers - 1; i++) {
            b = new double[layers.get(i + 1)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                b[j] = 0.0;
            }
            db.add(b);
        }
        for (int i = 0; i < numLayers - 1; i++) {
            w = new double[layers.get(i + 1)][layers.get(i)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    w[j][k] = 0.0;
                }
            }
            dwxSoS.add(w);
        }
        for (int i = 1; i < numLayers - 1; i++) {
            w = new double[layers.get(i)][layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    w[j][k] = 0.0;
                }
            }
            dwhSoS.add(w);
        }
        for (int i = 0; i < numLayers - 1; i++) {
            b = new double[layers.get(i + 1)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                b[j] = 0.0;
            }
            dbSoS.add(b);
        }
    }

    public double tanh(double x) {
        return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    public double tanhDerivative(double x) {
        return (Math.pow(Math.exp(x) + Math.exp(-x), 2) - Math.pow(Math.exp(x) - Math.exp(-x), 2)) / Math.pow(Math.exp(x) + Math.exp(-x), 2);
    }

    public double softmax(double x) {
        double denominator = 0.0;
        for (int i = 0; i < layers.get(numLayers - 1); i++) {
            denominator += Math.exp(aVal.get(numLayers - 1)[i]);
        }
        return Math.exp(x) / denominator;
    }

    public double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    public double sigmoidDerivative(double x){
        return Math.exp(-x) / Math.pow(1 + Math.exp(-x), 2);
    }

    public void costB(double x) {
        c += -1 * Math.log(x);
    }

    public void cost(double x) {
        c += -1 * Math.log(1 - x);
    }

    public double costDerivativeB(double x) {
        return -1 / x;
    }

    public double costDerivative(double x) {
        return 1 / (1 - x);
    }

    public double getCost() {
        return c;
    }

    public void setCost(double cSub) {
        c = cSub;
    }

    //The feedForward method feeds the input data through the network so the network can make a prediction based on the input data.
    public void feedForward(ArrayList<Double> wordData){
        for (int i = 0; i < numLayers; i++) {
            for (int j = 0; j < layers.get(i); j++) {
                hVal.get(i)[j] = 0.0;
            }
            if (i == 0) {
                for (int j = 0; j < wordData.size(); j++) {
                    hVal.get(i)[j] = sigmoid(wordData.get(j));
                }
            }
        }
        for (int i = 0; i < numLayers; i++) {
            for (int j = 0; j < layers.get(i); j++) {
                aVal.get(i)[j] = 0.0;
            }
        }
        for(int i = 1; i < numLayers; i++){
            for(int j = 0; j < hVal.get(i).length; j++){
                for(int k = 0; k < hVal.get(i - 1).length; k++){
                    aVal.get(i)[j] += hVal.get(i - 1)[k] * wx.get(i - 1)[j][k];
                }
                if(i < numLayers - 1){
                    for(int k = 0; k < phVal.get(i - 1).length; k++){
                        aVal.get(i)[j] += phVal.get(i - 1)[k] * wh.get(i - 1)[j][k];
                    }
                }
                hVal.get(i)[j] = sigmoid(aVal.get(i)[j] + biases.get(i - 1)[j]);
            }
        }
    }

    //the calculateCost method calculates the cost, and the cost is basically a measure of how wrong the network is. The higher the cost, the more wrong the network's prediction is.
    public void calculateCost(String label){
        int numLabel = 0;
        if (label.equals("pos")) {
            numLabel = 1;
        }
        for(int i = 0; i < hVal.get(hVal.size() - 1).length; i++){
            if(i == numLabel){
                costB(hVal.get(hVal.size() - 1)[i]);
            }
            else{
                cost(hVal.get(hVal.size() - 1)[i]);
            }
        }
    }

    public void addRegularization(){
        for(int i = 0; i < wx.size(); i++){
            for(int j = 0; j < wx.get(i).length; j++){
                for(int k = 0; k < wx.get(i)[j].length; k++){
                    regularization += Math.pow(wx.get(i)[j][k], 2);
                }
            }
        }
        for(int i = 0; i < wh.size(); i++){
            for(int j = 0; j < wh.get(i).length; j++){
                for(int k = 0; k < wh.get(i)[j].length; k++){
                    regularization += Math.pow(wh.get(i)[j][k], 2);
                }
            }
        }

    }

    //This is is probably the most important method. This method calculates the gradients. Gradients are basically the derivative the cost function (function used to calculate cost) with respect to a weight/bias. These weights are then subtracted from the weight/bias value itself in order to fine tune the network and make it more accurate (learning).
    public void backPropagation2(String label, double beta) {
        double holder = 0.0;
        int numLabel = 0;
        if (label.equals("pos")) {
            numLabel = 1;
        }
        ArrayList<double[][]> dwxi = new ArrayList<double[][]>();
        ArrayList<double[][]> dwhi = new ArrayList<double[][]>();
        ArrayList<double[]> dbi = new ArrayList<double[]>();
        for (int i = 0; i < numLayers - 1; i++) {
            dwxi.add(new double[layers.get(i + 1)][layers.get(i)]);
        }
        for (int i = 1; i < numLayers - 1; i++) {
            dwhi.add(new double[layers.get(i)][layers.get(i)]);
        }
        for (int i = 0; i < numLayers - 1; i++) {
            dbi.add(new double[layers.get(i + 1)]);
        }
        for(int i = 0; i < wx.get(numLayers - 2).length; i++){
            for(int j = 0; j < wx.get(numLayers - 2)[0].length; j++){
                if(i == numLabel){
                    dwxi.get(numLayers - 2)[i][j] = costDerivativeB(hVal.get(numLayers - 1)[i]) * sigmoidDerivative(aVal.get(numLayers - 1)[i]) * hVal.get(numLayers - 2)[j];
                }
                else{
                    dwxi.get(numLayers - 2)[i][j] = costDerivative(hVal.get(numLayers - 1)[i]) * sigmoidDerivative(aVal.get(numLayers - 1)[i]) * hVal.get(numLayers - 2)[j];
                }
            }
        }
        for(int i = numLayers - 3; i > -1; i--){
            for(int j = 0; j < wx.get(i).length; j++){
                for(int k = 0; k < wx.get(i)[j].length; k++){
                    for(int l = 0; l < wx.get(i + 1).length; l++){
                        dwxi.get(i)[j][k] += dwxi.get(i + 1)[l][j] / hVal.get(i + 1)[j] * wx.get(i + 1)[l][j] * sigmoidDerivative(aVal.get(i + 1)[j]) * hVal.get(i)[k];
                    }
                }
            }
        }
        for(int i = numLayers - 3; i > -1; i--){
            for(int j = 0; j < wh.get(i).length; j++){
                for(int k = 0; k < wh.get(i)[0].length; k++){
                    dwhi.get(i)[j][k] = dwxi.get(i)[j][0] / hVal.get(i)[0] * phVal.get(i)[k];
                }
            }
        }
        for(int i = 0; i < biases.get(numLayers - 2).length; i++){
            if(i == numLabel){
                dbi.get(numLayers - 2)[i] = costDerivativeB(hVal.get(numLayers - 1)[i]) * sigmoidDerivative(aVal.get(numLayers - 1)[i]);
            }
            else{
                dbi.get(numLayers - 2)[i] = costDerivative(hVal.get(numLayers - 1)[i]) * sigmoidDerivative(aVal.get(numLayers - 1)[i]);
            }
        }
        for(int i = numLayers - 3; i > -1; i--){
            for(int j = 0; j < biases.get(i).length; j++){
                for(int k = 0; k < biases.get(i + 1).length; k++){
                    dbi.get(i)[j] += dbi.get(i + 1)[k] * wx.get(i + 1)[k][j] * sigmoidDerivative(aVal.get(i + 1)[j]);
                }
            }
        }
        for(int i = 0; i < wx.size(); i++){
            for(int j = 0; j < wx.get(i).length; j++){
                for(int k = 0; k < wx.get(i)[j].length; k++){
                    dwx.get(i)[j][k] += dwxi.get(i)[j][k];
                    holder = dwxSoS.get(i)[j][k];
                    dwxSoS.get(i)[j][k] = beta * holder + (1 - beta) * Math.abs(Math.pow(dwxi.get(i)[j][k], 2));
                }
            }
        }
        for(int i = 0; i < wh.size(); i++){
            for(int j = 0; j < wh.get(i).length; j++){
                for(int k = 0; k < wh.get(i)[j].length; k++){
                    dwh.get(i)[j][k] += dwhi.get(i)[j][k];
                    holder = dwhSoS.get(i)[j][k];
                    dwhSoS.get(i)[j][k] = beta * holder + (1 - beta) * Math.abs(Math.pow(dwhi.get(i)[j][k], 2));
                }
            }
        }
        for(int i = 0; i < biases.size(); i++){
            for(int j = 0; j < biases.get(i).length; j++){
                db.get(i)[j] += dbi.get(i)[j];
                holder = dbSoS.get(i)[j];
                dbSoS.get(i)[j] = beta * holder + (1 - beta) * Math.abs(Math.pow(dbi.get(i)[j], 2));
            }
        }
        setPHVal();
    }

    public void setPHVal(){
        for(int i = 0; i < phVal.size(); i++){
            for(int j = 0; j < phVal.get(i).length; j++){
                phVal.get(i)[j] = hVal.get(i + 1)[j];
            }
        }
    }

    //This method subtracts the gradients from their respective weights/biases.
    public void subtract(double learningRate, double regularizationRate) {
        double w = 0;
        //regularization /= 200;
        c /= 100;
        //c += regularization;
        for (int i = 0; i < dwx.size(); i++) {
            for (int j = 0; j < dwx.get(i).length; j++) {
                for (int k = 0; k < dwx.get(i)[j].length; k++) {
                    w = wx.get(i)[j][k];
                    wx.get(i)[j][k] -= ((learningRate / Math.pow(dwxSoS.get(i)[j][k] + 0.00000001, 0.5)) * dwx.get(i)[j][k] / 100);
                }
            }
        }
        for (int i = 0; i < dwh.size(); i++) {
            for (int j = 0; j < dwh.get(i).length; j++) {
                for (int k = 0; k < dwh.get(i)[j].length; k++) {
                    w = wh.get(i)[j][k];
                    wh.get(i)[j][k] -= ((learningRate / Math.pow(dwhSoS.get(i)[j][k] + 0.00000001, 0.5)) * dwh.get(i)[j][k] / 100);
                }
            }
        }
        for (int i = 0; i < db.size(); i++) {
            for (int j = 0; j < db.get(i).length; j++) {
                biases.get(i)[j] -= ((learningRate / Math.pow(dbSoS.get(i)[j] + 0.00000001, 0.5)) * db.get(i)[j] / 100);
            }
        }
        dwx.clear();
        dwh.clear();
        db.clear();
        double[][] dwi = new double[0][0];
        for (int i = 0; i < numLayers - 1; i++) {
            dwi = new double[layers.get(i + 1)][layers.get(i)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                for (int k = 0; k < layers.get(i); k++) {
                    dwi[j][k] = 0.0;
                }
            }
            dwx.add(dwi);
        }
        double[][] dwhi = new double[0][0];
        for (int i = 0; i < numLayers - 2; i++) {
            dwh.add(new double[layers.get(i + 1)][layers.get(i + 1)]);

        }
        double[] dbi = new double[0];
        for (int i = 0; i < numLayers - 1; i++) {
            dbi = new double[layers.get(i + 1)];
            for (int j = 0; j < layers.get(i + 1); j++) {
                dbi[j] = 0.0;
            }
            db.add(dbi);
        }
    }

    public void clearPHVal() {
        phVal.clear();
        double[] ph = new double[0];
        for (int i = 1; i < numLayers - 1; i++) {
            ph = new double[layers.get(i)];
            for (int j = 0; j < layers.get(i); j++) {
                ph[j] = 0.0;
            }
            phVal.add(ph);
        }
    }

    public boolean compare(String label){
        double greatest = 0.0;
        double preLabel = 0.0;
        double numLabel = 0;
        if(label.equals("pos")){
            numLabel = 1;
        }
        for(int i = 0; i < hVal.get(numLayers - 1).length; i++){
            if(greatest <= hVal.get(numLayers - 1)[i]){
                greatest = hVal.get(numLayers - 1)[i];
                preLabel = i;
            }
        }
        if(preLabel == numLabel){
            return true;
        }
        else{
            return false;
        }
    }

    //This method allows the network to make a prediction. The sentiment that is predicted is associated with the neuron of greater value.
    public String predict(){
        int greatest = 0;
        for(int i = 0; i < hVal.get(numLayers - 1).length; i++){
            if(hVal.get(numLayers - 1)[greatest] <= hVal.get(numLayers - 1)[i]){
                greatest = i;
            }
        }
        if(greatest == 0){
            return "negative sentiment";
        }
        else {
            return "positive sentiment";
        }
    }


    public String toString() {
        String s = "";
        for (int i = 0; i < wx.size(); i++) {
            for (int j = 0; j < wx.get(i).length; j++) {
                for (int k = 0; k < wx.get(i)[j].length; k++) {
                    s += wx.get(i)[j][k] + " ";
                }
                s += "\n";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        for (int i = 0; i < wh.size(); i++) {
            for (int j = 0; j < wh.get(i).length; j++) {
                for (int k = 0; k < wh.get(i)[j].length; k++) {
                    s += wh.get(i)[j][k] + " ";
                }
                s += "\n";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        for (int i = 0; i < biases.size(); i++) {
            for (int j = 0; j < biases.get(i).length; j++) {
                s += biases.get(i)[j] + " ";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";

        return s;
    }

    public String toString2() {
        String s = "";
        for(int i = 0; i < hVal.size(); i++){
            for(int j = 0; j < hVal.get(i).length; j++){
                s += hVal.get(i)[j] + " ";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        for(int i = 0; i < aVal.size(); i++){
            for(int j = 0; j < aVal.get(i).length; j++){
                s += aVal.get(i)[j] + " ";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        for(int i = 0; i < phVal.size(); i++){
            for(int j = 0; j < phVal.get(i).length; j++){
                s += phVal.get(i)[j] + " ";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        return s;
    }

    public String toString3() {
        String s = "";
        for (int i = 0; i < dwxSoS.size(); i++) {
            for (int j = 0; j < dwxSoS.get(i).length; j++) {
                for (int k = 0; k < dwxSoS.get(i)[j].length; k++) {
                    s += dwxSoS.get(i)[j][k] + " ";
                }
                s += "\n";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        for (int i = 0; i < dwhSoS.size(); i++) {
            for (int j = 0; j < dwhSoS.get(i).length; j++) {
                for (int k = 0; k < dwhSoS.get(i)[j].length; k++) {
                    s += dwhSoS.get(i)[j][k] + " ";
                }
                s += "\n";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";
        for (int i = 0; i < dbSoS.size(); i++) {
            for (int j = 0; j < dbSoS.get(i).length; j++) {
                s += dbSoS.get(i)[j] + " ";
            }
            s += "\n";
        }
        s += "\n";
        s += "\n";
        s += "\n";

        return s;
    }
}
