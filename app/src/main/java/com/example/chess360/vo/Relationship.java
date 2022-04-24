package com.example.chess360.vo;

public class Relationship {

    private User followingUser;
    private User followedUser;

    public Relationship(User followingUser, User followedUser){
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }

    public User getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }

    @Override
    public boolean equals(Object o){

        boolean outcome = false;

        if (o instanceof Relationship){

            Relationship isRelationship = (Relationship) o;
            outcome = this.followingUser.equals(isRelationship.getFollowingUser()) &&
                    this.followedUser.equals(isRelationship.getFollowedUser());
        }

        return outcome;
    }
}
