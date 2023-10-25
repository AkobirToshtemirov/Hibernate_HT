import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Transaction transaction1 = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction1 = session.beginTransaction();

            Student student1 = aStudent("John", "Doe", "john@gmail.com");
            Student student2 = aStudent("Jane", "Smith", "jane@outlook.com");
            Student student3 = aStudent("Mike", "Johnson", "mike@hotmail.com");

            Group group1 = aGroup("First Group", List.of(student1, student2), 2026);
            Group group2 = aGroup("Second Group", List.of(student3), 2025);

            Course course1 = aCourse("Computer Science", "This course is about computer science", List.of(group1, group2));
            Course course2 = aCourse("Physics", "This course is about physics", List.of(group1));


            session.save(student1);
            session.save(student2);
            session.save(student3);
            session.save(group1);
            session.save(group2);
            session.save(course1);

            transaction1.commit();
        } catch (Exception e) {
            if (transaction1 != null) {
                transaction1.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String courseNameToSearch = "Software Development";
            List<Student> studentsInCourse = getStudentsByCourseName(session, courseNameToSearch);

            if (studentsInCourse != null) {
                System.out.println("Students in course " + courseNameToSearch + ":");
                for (Student student : studentsInCourse) {
                    System.out.println(student.getFirstName() + " " + student.getLastName());
                }
            } else {
                System.out.println("Course not found or no students in the course.");
            }
        }
    }

    private static List<Student> getStudentsByCourseName(Session session, String courseName) {
        Course course = getCourseByName(session, courseName);
        if (course != null) {
            return course.getGroups()
                    .stream()
                    .flatMap(group -> group.getStudents().stream())
                    .distinct()
                    .toList();
        }
        return null;
    }

    private static Course getCourseByName(Session session, String courseName) {
        Query<Course> query = session.createQuery("FROM Course c WHERE c.courseName = :courseName", Course.class);
        query.setParameter("courseName", courseName);
        return query.uniqueResult();
    }

    private static Student aStudent(String firstName, String lastName, String email) {
        return new Student(firstName, lastName, email);
    }

    private static Group aGroup(String groupName, List<Student> students, int yearOfgraduation) {
        return new Group(groupName, students, yearOfgraduation);
    }

    private static Course aCourse(String courseName, String courseDescription, List<Group> groups) {
        return new Course(courseName, courseDescription, groups);
    }
}