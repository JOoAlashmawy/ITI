public class Passenger {
    private String pClass;
    private String survived;
    private String name;
    private String sex;
    private float age;
    private String sibsp;  //Sibsp - Number of Siblings/Spouses Aboard
    private String parch;  //Parch - Number of Parents/Children Aboard
    private String ticket;
    private float fare;
    private String cabin;
    private String embarked;  //Embarked - Port of Embarkation (C = Cherbourg; Q = Queenstown; S = Southampton)
    private String boat;
    private String body;
    private String home_dist;

//    public Passenger(int pClass, String survived, String name, String sex, float age, int sibsp, String parch, String ticket, float fare, String cabin, String embarked, String boat, String body, String home_dist) {
//        this.pClass = pClass;
//        this.survived = survived;
//        this.name = name;
//        this.sex = sex;
//        this.age = age;
//        this.sibsp = sibsp;
//        this.parch = parch;
//        this.ticket = ticket;
//        this.fare = fare;
//        this.cabin = cabin;
//        this.embarked = embarked;
//        this.boat = boat;
//        this.body = body;
//        this.home_dist = home_dist;
//    }

    public String getPclass() {
        return pClass;
    }
    public String getSurvived() {
        return survived;
    }
    public String getName() {
        return name;
    }
    public String getSex() {
        return sex;
    }
    public float getAge() {
        return age;
    }
    public String getSibsp() {
        return sibsp;
    }
    public String getParch(){
        return parch;
    }
    public String getTicket() {
        return ticket;
    }
    public float getFare() {
        return fare;
    }
    public String getCabin() {
        return cabin;
    }
    public String getEmbarked() {
        return embarked;
    }
    public String getBoat() {
        return boat;
    }
    public String getBody() {
        return body;
    }
    public String getHome_dist() {
        return home_dist;
    }


    public String toString()
    {
        return "Passenger [ " +
                "Name: " + this.getName() + " "+
                "Age: " + this.getAge() + " "+
                "Ticket: " + this.getTicket() + " "
                + " ] \n";
    }


}
