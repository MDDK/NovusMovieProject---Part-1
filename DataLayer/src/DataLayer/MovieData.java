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
                films = storeLine(line, films);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return films;
    }
    
    
    
    private Films storeLine(String[] line, Films films){
        Films tmpFilms = films;
        
        
        if(tmpFilms.stream().anyMatch(item -> item.filmID.equals(line[AppVariables.filmID]))){
            Film tmpFilm = tmpFilms.stream().filter(item -> item.filmID.equals(line[AppVariables.filmID])).findFirst().get();//.collect(Collectors.toList()).get(0);

            if(tmpFilm.directors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.directorID]))){

            }else{
                Director director = this.getDirectorFromData(line);
                tmpFilm.directors.add(director);
            }
            if(tmpFilm.actors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.actorID]))){

            }else{
                Actor actor = this.getActorFromData(line);
                tmpFilm.actors.add(actor);
            }
        }else{
            Film film = this.getFilmFromData(line);
            tmpFilms.add(film);
        }
        
        return tmpFilms;
    }
    
    private Director getDirectorFromData(String[] line){
        Director director = new Director(line[AppVariables.directorID].trim(), 
                                         line[AppVariables.directorName].trim());
        return director;
    }
    
    private Actor getActorFromData(String[] line){
        Actor actor = new Actor(line[AppVariables.actorID].trim(), 
                                line[AppVariables.actorName].trim());
        return actor;
    }
    
    private Film getFilmFromData(String[] line){
        
        Director director = this.getDirectorFromData(line);
        Actor actor = this.getActorFromData(line);
        
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
