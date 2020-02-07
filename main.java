import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class main {
  private static Node head;
  private static Node current;
  private static ArrayList<Action> future_actions = new ArrayList<Action>();
  private static ArrayList<Measurement> future_measurements = new ArrayList<Measurement>();
  private static int time_step = 0;

  public static void main(String args[]) {
    initialize();
    head.view_current_state();
    current = head;
    for (int i = 0; i < future_actions.size(); i++) {
      iterate_over_time_step();
    }
  }

  private static void initialize() {
    // all possible states
    State OPEN = new State("OPEN", 0.5);
    State CLOSED = new State("CLOSED", 0.5);
    ArrayList<State> states = new ArrayList<State>(Arrays.asList(OPEN, CLOSED));

    // all possible actions
    Action PUSH = new Action("PUSH");
    Action NOTHING = new Action("NOTHING");

    // all possible measurements
    Measurement SENSE_OPEN = new Measurement("SENSE_OPEN");
    Measurement SENSE_CLOSED = new Measurement("SENSE_CLOSED");

    // our maps and probabilities
    HashMap<String, Double> state_trans = new HashMap<String, Double>();
    HashMap<String, Double> measurement_prob = new HashMap<String, Double>();
    state_trans.put(form_action_string(OPEN, PUSH, OPEN), 1.0);
    state_trans.put(form_action_string(CLOSED, PUSH, OPEN), 0.0);
    state_trans.put(form_action_string(OPEN, PUSH, CLOSED), 0.8);
    state_trans.put(form_action_string(CLOSED, PUSH, CLOSED), 0.2);
    state_trans.put(form_action_string(OPEN, NOTHING, OPEN), 1.0);
    state_trans.put(form_action_string(CLOSED, NOTHING, OPEN), 0.0);
    state_trans.put(form_action_string(OPEN, NOTHING, CLOSED), 0.0);
    state_trans.put(form_action_string(CLOSED, NOTHING, CLOSED), 1.0);
    measurement_prob.put(form_measuremenent_string(SENSE_OPEN, OPEN), 0.6);
    measurement_prob.put(form_measuremenent_string(SENSE_CLOSED, OPEN), 0.4);
    measurement_prob.put(form_measuremenent_string(SENSE_OPEN, CLOSED), 0.2);
    measurement_prob.put(form_measuremenent_string(SENSE_CLOSED, CLOSED), 0.8);

    // our future actions and measurements
    future_actions.add(NOTHING);
    future_measurements.add(SENSE_OPEN);
    future_actions.add(PUSH);
    future_measurements.add(SENSE_OPEN);
    
    // initialize our node at time 0
    head = new Node(states, state_trans, measurement_prob);
  }

  private static void iterate_over_time_step() {

    current.predict(future_actions.get(time_step));
    current.view_prediction(future_actions.get(time_step));

    current = current.compute(future_measurements.get(time_step));
    current.view_current_state(future_measurements.get(time_step));

    time_step++;
  }

  private static String form_action_string(State Xt, Action Ut, State Xt_) {
    return Xt.get_name() + " | " + Ut.get_name() + " & " + Xt_.get_name();
  }

  private static String form_measuremenent_string(Measurement Zt, State Xt) {
    return Zt.get_name() + " | " + Xt.get_name();
  }
}
