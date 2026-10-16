package Projectp;
public class Project extends Item {
    private String team;
    private String level;

    public Project() {
        super();
    }

    public Project(String level) {
        this.level = level;
    }

    public Project(String level, int itemId, String title, int year, String subject, String team) {
        super(itemId, title, year, subject);
        this.level = level;
        this.team = team;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public void printinfo() {
        System.out.println("The itemId of book = " + getItemId());
        System.out.println("The title is " + getTitle());
        System.out.println("The year of uplode = " + getYear());
        System.out.println("The subject  = " + getSubject());
        System.out.println("The team name is " + getTeam());
        System.out.println("The level of project is " + getLevel());
    }

}
