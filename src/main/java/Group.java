import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"GROUP\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "group_student",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    @Column(name = "year_of_graduation")
    private int yearOfGraduation;

    public Group() {
        // Default constructor
    }

    public Group(String groupName, List<Student> students, int yearOfGraduation) {
        this.groupName = groupName;
        this.students = students;
        this.yearOfGraduation = yearOfGraduation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(int yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", groupName=" + groupName + ", yearOfGraduation=" + yearOfGraduation + "]";
    }
}