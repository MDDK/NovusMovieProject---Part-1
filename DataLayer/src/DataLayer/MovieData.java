package DataLayer;

import ClassLayer.*;
import ApplicationVariables.AppVariables;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;


public class MovieData {
    
    String message = "";
    
    //read data from CSV file - path provided as param
    public Films getFilmData(String csvPath){
        Films films = new Films();
        String[] line;
        
        try(CSVReader csv = new CSVReader(new FileReader(csvPath));){
            String[] headers = csv.readNext(); //read first line for header strings
           
            while((line = csv.readNext()) != null){
                films = storeLine(line, films); //store each line as film data in films
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return films;
    }
    
    
    
    private Films storeLine(String[] line, Films films){
        Films tmpFilms = films;
        
        
        if(tmpFilms.stream().anyMatch(item -> item.filmID.equals(line[AppVariables.filmID]))){ //Is there already a duplicate film with that ID?
            //get the first instance of a film with that id which has a duplicate and save it to tmpFilm
            Film tmpFilm = tmpFilms.stream().filter(item -> item.filmID.equals(line[AppVariables.filmID])).findFirst().get();//.collect(Collectors.toList()).get(0);
            //Is the directorId we just passed, one of the directors in tmpFilm?
            if(tmpFilm.directors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.directorID]))){

            }else{//If not then add a Director object with that ID to tmpFilm
                Director director = this.getDirectorFromData(line);
                tmpFilm.directors.add(director);
            }
            //Is the actorId we just passed, one of the directors in tmpFilm?
            if(tmpFilm.actors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.actorID]))){

            }else{ //If not then add an Actor object with that ID to tmpFilm
                Actor actor = this.getActorFromData(line);
                tmpFilm.actors.add(actor);
            }
        }else{ //No existing films with the same ID? Just add film based on the data we are passing to tmpFilms
            Film film = this.getFilmFromData(line);
            tmpFilms.add(film);
        }
        
        return tmpFilms;
    }
    
    private Director getDirectorFromData(String[] line){
        //Create a new Director from the director ID and Name values found in line array
        Director director = new Director(line[AppVariables.directorID].trim(), 
                                         line[AppVariables.directorName].trim());
        return director;
    }
    
    private Actor getActorFromData(String[] line){
        //Create a new Actor from the director ID and Name values found in line array
        Actor actor = new Actor(line[AppVariables.actorID].trim(), 
                                line[AppVariables.actorName].trim());
        return actor;
    }
    
    private Film getFilmFromData(String[] line){ //Take data from line array and create a Film object from it
        
        Director director = this.getDirectorFromData(line); //Director object created using data from line array
        Actor actor = this.getActorFromData(line); //Actor object created using data from line array
        //filmID, filmName etc. give the index number for their respective values, NOT the values themselves
        Film film = new Film(line[AppVariables.filmID].trim(), 
                             line[AppVariables.filmName].trim(),
                             line[AppVariables.imdbRating].trim(),
                             line[AppVariables.filmYear].trim());
        film.directors.add(director);
        film.actors.add(actor);
        
        return film; 
    }
    
   

    
    public String getResultMessage(){
        return message;
    }
}
