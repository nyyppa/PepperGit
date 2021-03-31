package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {
    private Chat chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);
    }
 //1232
    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        
        //Topic topic = TopicBuilder.with(qiContext).withResource(R.raw.esimerkki).build(); //Gets topic file for chatbot to use

        List<Topic>topics=new ArrayList<>();
        //Create list to hold topics
        topics.add(TopicBuilder.with(qiContext).withResource(R.raw.esimerkki).build());
        //add topic to the list
        topics.add(TopicBuilder.with(qiContext).withResource(R.raw.food).build());
        //add topic to the list
        topics.add(TopicBuilder.with(qiContext).withResource(R.raw.introduction).build());
        //add topic to the list
        QiChatbot qiChatbot = QiChatbotBuilder.with(qiContext).withTopics(topics).build();
        //Creates QiChatbot for Chat to use
        
        chat = ChatBuilder.with(qiContext).withChatbot(qiChatbot).build(); //Creates chat using the chatbot and gives it languege to speak
        chat.addOnStartedListener(() -> Log.i("Testi", "Discussion started.")); //adds listener for when chat is started and prints to log when it's going.
        Future<Void> chatFuture = chat.async().run(); //Starts the chat asyncronically
        //prints if chat has error starting
        chatFuture.thenConsume(future -> {
            if(future.hasError()){
                Log.e("Error", "Discussion finished with error.", future.getError());
            }
        });

        // The robot focus is gained.
        // Create a new say action.
        /*
        Say say = SayBuilder.with(qiContext) // Create the builder with the context.
                .withText("Hello human!") // Set the text to say.
                .build(); // Build the say action.
        // Execute the action.
        say.run();

        PhraseSet phraseSet = PhraseSetBuilder.with(qiContext) //Create the builder with the context
                .withTexts("Hello", "Hi") //Set Phrases we are looking for
                .build(); // Build the phrase set

        Listen listen = ListenBuilder.with(qiContext) //CreateCreate the builder with the context.
                .withPhraseSet(phraseSet) //Give Phrases to listen action
                .build(); // Build the listen action

        ListenResult listenResult = listen.run();
        Log.i("testi", "Heard phrase: " + listenResult.getHeardPhrase().getText()); // Prints heard phrase.

        //Checking if we heard what we wanted to hear and speak after that
        if(phraseSet.getPhrases().contains(listenResult.getHeardPhrase())){
            SayBuilder.with(qiContext).withText("how are you?").build().run();
        }*/

        Animation animation= AnimationBuilder.with(qiContext).withResources(R.raw.animation_00).build();
        //builds Animation
        Animate animate = AnimateBuilder.with(qiContext).withAnimation(animation).build();
        //builds runnable animation
        animate.run();
        //starts the animation

        /*qiChatbot.addOnFocusedTopicChangedListener(new QiChatbot.OnFocusedTopicChangedListener() {
            @Override
            public void onFocusedTopicChanged(Topic topic) {
                Log.i("topic",topic.getName());
                if(topic.getName().equals("food")){
                    animate.run();
                }
            }
        });*/



    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        if(chat!=null){
            chat.removeAllOnStartedListeners();
        }
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}