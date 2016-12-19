package com.tlproject.omada1.tl_project.Controller;

import android.location.Location;
import android.renderscript.Double2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlproject.omada1.tl_project.GPSTrack.GPSTracker;
import com.tlproject.omada1.tl_project.Model.Quest;
import com.tlproject.omada1.tl_project.Model.User;

/**
 * Created by Chris on 4/11/2016.
 */

public class QuestController implements QuestInterface {
    @Override
    public void NextQuest(final Quest CurQuest) {
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Quest");
        String nextquest = String.valueOf(CurQuest.getNextIdQuest());
        dbref.child("Quest"+nextquest).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String descQuest=dataSnapshot.child("desc").getValue(String.class);
                String expQuest=dataSnapshot.child("exp").getValue(String.class);
                String latQuest=dataSnapshot.child("lat").getValue(String.class);
                String lngQuest=dataSnapshot.child("long").getValue(String.class);
                String nextquestidQuest=dataSnapshot.child("nextquestid").getValue(String.class);
                String questidQuest=dataSnapshot.child("questid").getValue(String.class);
                CurQuest.setQuest(questidQuest+";"+descQuest+";"+expQuest+";"+nextquestidQuest+";"+latQuest+";"+lngQuest+";");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean QuestIsTrue(Quest CurQuest) {
        return (CurQuest.getLat()!=0 && CurQuest.getLng()!=0);
    }

    @Override
    public boolean checkAction(Quest quest, User User, int QuestRadius,Double Lat,Double Long){
                Location loc1,loc2;
                loc1=new Location("");
                loc1.setLatitude(Lat);
                loc1.setLongitude(Long);
                loc2=new Location("");
                loc2.setLatitude( quest.getLat());
                loc2.setLongitude(quest.getLng());
                if (loc1.distanceTo(loc2) <= QuestRadius) {
                    new UserController().QuestComplete(User, quest);
                    NextQuest(quest);
                    return true;
                }
        return false;
    }
    @Override
    public boolean checkQuestMark(Quest quest,int QuestRadius,Double Lat,Double Long){
        Location loc1,loc2;
        loc1=new Location("");
        loc1.setLatitude(Lat);
        loc1.setLongitude(Long);
        loc2=new Location("");
        loc2.setLatitude( quest.getLat());
        loc2.setLongitude(quest.getLng());
        if (loc1.distanceTo(loc2) <= QuestRadius) {
            return true;
        }
        return false;
    }
}
