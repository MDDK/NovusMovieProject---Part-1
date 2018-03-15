package ApplicationVariables;


public class AppVariables {
    
    
    public static int filmID = 0;
    public static int filmName = 1;
    public static int imdbRating = 2;
    public static int filmYear = 7;
    public static int directorID = 3;
    public static int directorName = 4;
    public static int actorID = 5;
    public static int actorName = 6;
        
    public static class CSV{    
        
        public static String FILE_PATH = "C:\\Users\\Marek\\Documents\\NetBeansProjects\\NovusMovieProject - Part 1\\Data\\TestData.csv";
        public static String EXTENDED_FILE_PATH = "C:\\Users\\Marek\\Documents\\NetBeansProjects\\NovusMovieProject - Part 1\\Data\\ExtendedTestData.csv";
    } 
      
    public static class WebProperties{
       
        public static String imdbFilmURL = "http://www.imdb.com/title/tt%s";
        public static String imdbProfileURL = "http://www.imdb.com/name/nm%s";
        
        
        public static final String dropDownDefault = "--SELECT--";
        
       
        public static String yearMin = "2000";
        public static String yearMax = "2017";
    }   
}
