class State {
  private String name;
  private double prob;

  public State(String name, double prob) {
    this.name = name;
    this.prob = prob;
  }

  public String get_name() {
    return this.name;
  }

  public Double get_prob() {
    return this.prob;
  }

  public void normalize(double constant) {
    this.prob *= constant;
  }
}
