package com.example.chess360.dao;

import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;

import com.example.chess360.vo.*;

import java.sql.Array;
import java.util.ArrayList;

public abstract class Dao {

    private static int current_id_user = 1;
    private static int current_id_tournament = 1;
    private static int current_id_game = 1;
    private static int current_id_achievement = 1;

    // Checks whether the arraylists have been initialized:
    private static boolean initialized = false;

    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<Club> clubs = new ArrayList<>();
    private static final ArrayList<Organizer> organizers = new ArrayList<>();
    private static final ArrayList<Tournament> tournaments = new ArrayList<>();
    private static final ArrayList<Game> games = new ArrayList<>();
    private static final ArrayList<TournamentRecord> tournamentRecords = new ArrayList<>();
    private static final ArrayList<Post> posts = new ArrayList<>();
    private static final ArrayList<Relationship> relationships = new ArrayList<>();
    private static final ArrayList<Achievement> achievements = new ArrayList<>();
    private static final ArrayList<CompleteAchievement> completeAchievements = new ArrayList<>();

    // Users
    public static void addUser(User newUser){
        users.add(newUser);
    }

    public static ArrayList<User> getUsers(){
        return(users);
    }

    public static int getUserIndex(User user){
        return(users.indexOf(user));
    }

    public static User getUser(String username){

        User newUser = new User(username);
        int index = Dao.getUserIndex(newUser);

        User myUser = null;

        if (index != -1){

            myUser = Dao.getUsers().get(index);
        }

        return myUser;
    }

    public static User getUserByName(String name){

        User myUser = null;

        boolean found = false;
        int index = 0;

        while(!found && index<users.size()){

            User user = users.get(index);
            String userName = new String();

            if (user instanceof Player){

                userName = ((Player) user).getName() + " " + ((Player) user).getSurname();
            }
            else if (user instanceof Club){

                userName = ((Club) user).getName();
            }
            else if (user instanceof Organizer){

                userName = ((Organizer) user).getName();
            }

            found = name.equals(userName);

            if (!found){
                index++;
            }
        }

        if (found){

            myUser = users.get(index);
        }

        return myUser;
    }

    public static void deleteUser(String username){

        // First, we delete all the relationships where this user is involved:
        for (int i=0; i<relationships.size(); i++){
            Relationship myRelationship = relationships.get(i);
            if (myRelationship.getFollowingUser().getUsername().equals(username) ||
                myRelationship.getFollowedUser().getUsername().equals(username)){

                relationships.remove(i);
                i--;
            }
        }

        // Then, we delete all the posts made by this user:
        for (int i=0; i<posts.size(); i++){
            Post myPost = posts.get(i);
            if (myPost.getUser().getUsername().equals(username)){
                posts.remove(i);
                i--;
            }
        }

        // Then, we delete the user from the list of players/clubs/organizers:
        User myUser = Dao.getUser(username);
        int index;

        if (myUser instanceof Player){
            index = Dao.getPlayerIndex((Player) myUser);
            players.remove(index);
        }
        else if (myUser instanceof Club){
            index = Dao.getClubIndex((Club) myUser);
            clubs.remove(index);
        }
        else if (myUser instanceof Organizer){
            index = Dao.getOrganizerIndex((Organizer) myUser);
            organizers.remove(index);
        }

        // Finally, we delete the user from the list of users:
        index = Dao.getUserIndex(myUser);
        users.remove(index);
    }

    // Players
    public static void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    public static ArrayList<Player> getPlayers(){
        return(players);
    }

    public static int getPlayerIndex(Player player){
        return(players.indexOf(player));
    }

    public static Player getPlayer(String name){

        Player newPlayer = new Player(name);
        int index = Dao.getPlayerIndex(newPlayer);

        Player myPlayer = null;

        if (index != -1){

            myPlayer = Dao.getPlayers().get(index);
        }

        return myPlayer;
    }

    // Clubs
    public static void addClub(Club newClub){
        clubs.add(newClub);
    }

    public static ArrayList<Club> getClubs(){
        return(clubs);
    }

    public static int getClubIndex(Club club){
        return(clubs.indexOf(club));
    }

    public static Club getClub(String name){

        Club newClub = new Club(name);
        int index = Dao.getClubIndex(newClub);

        Club myClub = null;

        if (index != -1){

            myClub = Dao.getClubs().get(index);
        }

        return myClub;
    }

    // Organizers
    public static void addOrganizer(Organizer newOrganizer){
        organizers.add(newOrganizer);
    }

    public static ArrayList<Organizer> getOrganizers(){
        return(organizers);
    }

    public static int getOrganizerIndex(Organizer organizer){
        return(organizers.indexOf(organizer));
    }

    public static Organizer getOrganizer(String name){

        Organizer newOrganizer = new Organizer(name);
        int index = Dao.getOrganizerIndex(newOrganizer);

        Organizer myOrganizer = null;

        if (index != -1){

            myOrganizer = Dao.getOrganizers().get(index);
        }

        return myOrganizer;
    }

    // Tournaments
    public static void addTournament(Tournament newTournament){
        tournaments.add(newTournament);
    }

    public static ArrayList<Tournament> getTournaments(){
        return(tournaments);
    }

    public static int getTournament(Tournament tournament){
        return(tournaments.indexOf(tournament));
    }

    // Games
    public static void addGame(Game newGame){
        games.add(newGame);
    }

    public static ArrayList<Game> getGames(){
        return(games);
    }

    public static int getGame(Game game){
        return(games.indexOf(game));
    }

    // Posts:
    public static void addPost(Post newPost){
        posts.add(newPost);
    }

    public static ArrayList<Post> getPosts(){
        return posts;
    }

    // Posts made by a user:
    public static ArrayList<Post> getPosts(String username){

        ArrayList<Post> myPosts = new ArrayList<>();

        for (int i=0; i<posts.size(); i++){

            String currentUser = posts.get(i).getUser().getUsername();

            if (username.equals(currentUser)){
                myPosts.add(posts.get(i));
            }
        }

        return myPosts;
    }

    // Posts available for a user:
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Post> getAvailablePosts(String username){

        ArrayList<Post> myPosts = new ArrayList<>();

        // First, we fetch the posts made by the user:
        myPosts = Dao.getPosts(username);

        // Then, we retrieve all the users that this user follows:
        ArrayList<User> following = new ArrayList<>();

        for (int i=0; i<relationships.size(); i++){

            if (relationships.get(i).getFollowingUser().getUsername().equals(username)){
                following.add(relationships.get(i).getFollowedUser());
            }
        }

        // We add the posts made by the following users:
        for (int i=0; i<following.size(); i++){
            ArrayList<Post> userPosts = Dao.getPosts(following.get(i).getUsername());

            for (int j=0; j<userPosts.size(); j++){
                myPosts.add(userPosts.get(j));
            }
        }

        // Finally, the posts are sorted by date:
        Dao.sortPostsByDate(myPosts);

        return myPosts;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sortPostsByDate(ArrayList<Post> myPosts){

        boolean sorted = false;
        Post temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < myPosts.size() - 1; i++) {
                if (myPosts.get(i).getDate().isAfter(myPosts.get(i+1).getDate())) {
                    temp = myPosts.get(i);
                    myPosts.set(i,myPosts.get(i+1));
                    myPosts.set(i+1,temp);
                    sorted = false;
                }
            }
        }
    }

    public static int getPostIndex(Post myPost){
        return posts.indexOf(myPost);
    }

    public static Post getPost(int index){
        Post myPost = null;

        if (index >= 0 && index <posts.size()){
            myPost = posts.get(index);
        }

        return myPost;
    }

    // Tournament records
    public static void addTournamentRecord(TournamentRecord newTournamentRecord){
        tournamentRecords.add(newTournamentRecord);
    }

    public static ArrayList<TournamentRecord> getTournamentRecords(){
        return(tournamentRecords);
    }

    public static int getTournamentRecord(TournamentRecord tournamentRecord){
        return(tournamentRecords.indexOf(tournamentRecord));
    }

    public static int generateID_User(){
        Dao.current_id_user++;
        return Dao.current_id_user -1;
    }

    public static int generateID_Tournament(){
        Dao.current_id_tournament++;
        return Dao.current_id_tournament -1;
    }

    public static int generateID_Game(){
        Dao.current_id_game++;
        return Dao.current_id_game -1;
    }

    // Following:
    public static void addRelationship(Relationship newRelationship){
        relationships.add(newRelationship);
    }

    public static ArrayList<Relationship> getRelationships(){
        return(relationships);
    }

    public static int getRelationshipIndex(Relationship relationship){
        return(relationships.indexOf(relationship));
    }

    public static void deleteRelationship(Relationship relationship){
        int index = Dao.getRelationshipIndex(relationship);

        if (index != -1){

            Dao.relationships.remove(index);
        }
    }

    public static Relationship getRelationship(int index){

        Relationship myRelationship = null;

        if (index >= 0 && index < relationships.size()){
            myRelationship = relationships.get(index);
        }

        return myRelationship;
    }

    // Achievements:
    public static int generateID_Achievement(){
        Dao.current_id_achievement++;
        return Dao.current_id_achievement -1;
    }

    public static void addAchievement(Achievement newAchievement){
        achievements.add(newAchievement);
    }

    public static ArrayList<Achievement> getAchievements(){
        return(achievements);
    }

    public static int getAchievementIndex(Achievement achievement){
        return(achievements.indexOf(achievement));
    }

    public static Achievement getAchievement(int index){

        return achievements.get(index);
    }

    public static ArrayList<CompleteAchievement> getCompleteAchievements(){
        return completeAchievements;
    }

    /********************************************************************************************/

    public static void initialize(){

        if (!Dao.initialized){

            Dao.initialized = true;

            // Add several users:
            Player myPlayer = new Player("Martín", "Mato Búa", "martincheckmate",
                    "martinmb.dam@gmail.com", 2000, "whvw");
            Club myClub = new Club("Club de Ajedrez Maracena", "camaracena", "Granada", "686626393",
                    "caMaracena@gmail.com", "whvw");
            Organizer myOrganizer = new Organizer("Abanca", "abanca", "Santiago de Compostela",
                    "986180760", "abanca@gmail.com", "whvw");

            Dao.addPlayer(myPlayer);
            Dao.addClub(myClub);
            Dao.addOrganizer(myOrganizer);

            Dao.addUser(myPlayer);
            Dao.addUser(myClub);
            Dao.addUser(myOrganizer);

            // Usuario para hacer pruebas:
            Player test = new Player("test","test test", "test","fakeMail@test.com", 1500,"whvw");
            Dao.addUser(test);
            Dao.addPlayer(test);

            // Publicaciones para hacer pruebas:
            Post post1 = new Post("Buenas tardes",myPlayer);
            Post post2 = new Post("Esto es un test",test);
            Post post3 = new Post("¡Únete al club!",myClub);
            Post post4 = new Post("Torneo próximamente",myOrganizer);

            Dao.addPost(post1);
            Dao.addPost(post2);
            Dao.addPost(post3);
            Dao.addPost(post4);

            // Achievements:
            Achievement ach1 = new Achievement("Registro completado", "Has completado tu registro");
            Achievement ach2 = new Achievement("Primera partida", "Has jugado tu primera partida contra otra persona");
            Achievement ach3 = new Achievement("Primera victoria", "Has ganado tu primera partida contra otra persona");
            Achievement ach4 = new Achievement("Primera partida contra el ordenador","Has jugado tu primera partida contra el ordenador");
            Achievement ach5 = new Achievement("Primera victoria contra el ordenador", "Has ganado tu primera partida contra el ordenador");
            Achievement ach6 = new Achievement("Miembro de un club","Te has unido a un club");
            Achievement ach7 = new Achievement("Seguidor de un jugador","Has seguido a un jugador");
            Achievement ach8 = new Achievement("Seguidor de un club", "Has seguido a un club");
            Achievement ach9 = new Achievement("Seguidor de un organizador", "Has seguido a un organizador");
            Achievement ach10 = new Achievement("Mi primer torneo", "Te has inscrito en un torneo");
            Achievement ach11 = new Achievement("Mi primera publicación", "Has hecho tu primera publicación");
        }
    }

    public static boolean isPlayer(String user){
        return Dao.getPlayerIndex(new Player(user)) != -1;
    }

    public static boolean isClub(String user){
        return Dao.getClubIndex(new Club(user)) != -1;
    }

    public static boolean isOrganizer(String user){
        return Dao.getOrganizerIndex(new Organizer(user)) != -1;
    }

}
