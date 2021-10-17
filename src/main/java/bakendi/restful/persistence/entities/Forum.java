package bakendi.restful.persistence.entities;

// þurfum þetta örugglega til að viðhalda upplýsingar um kúrsana
public class Forum {
    private long id;

    private String courseId; // sbr TÖL025M eða eitthvað
    private String name;
    private String description;
    private List<long> threads;

    // ásett ráð að hafa ekki descr í constructor
    public Forum(long id, String courseId, String courseName) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
    }


    // á meðan allt er í lausu lofti þá ætla ég ekki að setja inn getter og setter.


    // kannski baeta við duration eða eithtvað...

    }
