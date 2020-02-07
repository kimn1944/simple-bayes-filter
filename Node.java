import java.util.ArrayList;
import java.util.HashMap;

class Node {
  private ArrayList<State> states;
  private ArrayList<State> predictions;
  private Node ptr;
  private static int time_stamp_global = 0;
  private int time_stamp;
  private HashMap<String, Double> state_trans;
  private HashMap<String, Double> measurement_prob;

  public Node(ArrayList<State> states, HashMap<String, Double> state_trans,  HashMap<String, Double> measurement_prob) {
    this.states = states;
    this.state_trans = state_trans;
    this.measurement_prob = measurement_prob;
    this.predictions = new ArrayList<State>();
    this.ptr = null;
    this.time_stamp = time_stamp_global;
    time_stamp_global++;
  }

  public void predict(Action action) {
    for (State state : this.states) {
      double prob = 0;
      for (State belief : this.states) {
        prob += this.state_trans.get(state.get_name() + " | " + action.get_name() + " & " + belief.get_name()) * belief.get_prob();
      }
      State new_prediction = new State(state.get_name(), prob);
      this.predictions.add(new_prediction);
    }
  }

  public Node compute(Measurement measurement) {
    ArrayList<State> temp = new ArrayList<State>();
    double non_normalized;
    double constant = 0;
    for (State prediction : this.predictions) {
      non_normalized = this.measurement_prob.get(measurement.get_name() + " | " + prediction.get_name()) * prediction.get_prob();
      constant += non_normalized;
      State temp_state = new State(prediction.get_name(), non_normalized);
      temp.add(temp_state);
    }
    constant = 1 / constant;
    for (State non_normalized_state : temp) {
      non_normalized_state.normalize(constant);
    }

    this.ptr = new Node(temp, this.state_trans, this.measurement_prob);

    return this.ptr;
  }

  public void view_prediction(Action action) {
    System.out.println("Prediction of probability distribution at time " + (this.time_stamp + 1) + " after action " + action.get_name() + ".");
    for (State belief : this.predictions) {
      System.out.println(belief.get_name() + " : " + belief.get_prob());
    }
  }

  public void view_current_state() {
    System.out.println("Probability distribution at time " + this.time_stamp + ".");
    for (State state : this.states) {
      System.out.println(state.get_name() + " : " + state.get_prob());
    }
  }

  public void view_current_state(Measurement measurement) {
    System.out.println("Probability distribution at time " + this.time_stamp + " after measurement " + measurement.get_name() + ".");
    for (State state : this.states) {
      System.out.println(state.get_name() + " : " + state.get_prob());
    }
  }
}
