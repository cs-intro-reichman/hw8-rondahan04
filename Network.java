

/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i<users.length;i++){
            if (users[i] != null && users[i].getName().equals(name)) //if its not null and it found a users name that match it enters
            return users[i];
        }
        return null;
    }
    public int getUserCount () {
        return this.userCount;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (this.userCount == this.users.length) // if its fail remove 1 from userslength
        return false;
        for (int i = 0; i < this.users.length; i++) {
            if (this.users[i] == null) {
                users[i] = new User(name); // creates the new one
                this.userCount++; // updates counter
                return true;
            }
        }
        return false;
    }
    // returns true if name1 is a user in the network,otherwise false
    public boolean isUser (String name1){
    for (int i = 0; i<this.users.length;i++) {
        if (this.users[i]!= null && this.users[i].getName().equals(name1))
        return true;
    }
        return false;
    }
    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1.equals(name2) || name1.equals(null) || name2.equals(null)) return false;
        if (!isUser(name1) || !isUser(name1)) // if one of them isnt a user in the network
        return false;
        for (int i = 0 ; i < this.users.length; i++){
            if (this.users[i] != null && this.users[i].getName().equals(name1)){
                if (!this.users[i].follows(name2)){ // if he doesnt follows name 2 make him follow!
                    this.users[i].addFollowee(name2);
                    return true;
                }
            }
        }
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int biggestM=0;
        User nameUser = null;
        int userIndex = 0;
        String recommendeduser="";
        for (int i = 0 ; i < this.users.length; i++){
            if (this.users[i]!= null && this.users[i].getName().equals(name)){
                nameUser = this.users[i];
                userIndex = i;
                break;
            }
        }
        for (int j = 0; j<this.users.length;j++){
            if (userIndex != j && users[j]!= null){
                int mutualf= mutualFollowers(nameUser, this.users[j]);
                if (mutualf > biggestM){
                recommendeduser=users[j].getName();
                biggestM=mutualf;
                }
            }
            }
        return recommendeduser;
    }
    //recieves 2 users one and two and returns how many mutual followers they have
    public int mutualFollowers (User one ,User two){
        int mutualCounter = 0;
        String [] followsOne = one.getfFollows();
        String [] followsTwo = two.getfFollows();
        for (int i = 0 ; i<followsOne.length; i++){
            for (int j = 0 ; j<followsTwo.length; j++){
                if (followsTwo[j]!= null && followsTwo[j].equals(followsOne[i])){
                mutualCounter++;
                break;  
                }
            }
        }
        return mutualCounter;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int maxFollowers = 0;
        if (users.length == 0)
        return null;
        int mostPopIndex = 0;
        for (int i = 0; i<this.users.length;i++){
            if (this.users[i]!= null && maxFollowers < this.users[i].getfCount()){
                maxFollowers = this.users[i].getfCount();
                mostPopIndex = i;
            }
        }
        return users[mostPopIndex].getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int followCount=0;
        for (int i = 0 ; i < this.users.length; i++){
            if (this.users[i] !=  null && this.users[i].follows(name)){
                followCount++;
            }
        }
        return followCount;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() { // \n is space down
        if (userCount == 0) return "Network:";
        String output ="Network:" + "\n";
       for (int i = 0 ; i<this.userCount -1;i++){
        if (users[i] != null)
        output = output + this.users[i].toString() +"\n";
       }
       output = output + this.users[this.userCount -1].toString();
       return output;
    }
}
