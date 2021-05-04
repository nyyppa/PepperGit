package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.GoToBuilder;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.LocalizeAndMapBuilder;
import com.aldebaran.qi.sdk.builder.LocalizeBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TakePictureBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.builder.TransformBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Actuation;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.actuation.ExplorationMap;
import com.aldebaran.qi.sdk.object.actuation.Frame;
import com.aldebaran.qi.sdk.object.actuation.FreeFrame;
import com.aldebaran.qi.sdk.object.actuation.GoTo;
import com.aldebaran.qi.sdk.object.actuation.LocalizationStatus;
import com.aldebaran.qi.sdk.object.actuation.Localize;
import com.aldebaran.qi.sdk.object.actuation.LocalizeAndMap;
import com.aldebaran.qi.sdk.object.actuation.MapTopGraphicalRepresentation;
import com.aldebaran.qi.sdk.object.actuation.Mapping;
import com.aldebaran.qi.sdk.object.camera.TakePicture;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.geometry.Transform;
import com.aldebaran.qi.sdk.object.human.AttentionState;
import com.aldebaran.qi.sdk.object.human.EngagementIntentionState;
import com.aldebaran.qi.sdk.object.human.ExcitementState;
import com.aldebaran.qi.sdk.object.human.Gender;
import com.aldebaran.qi.sdk.object.human.Human;
import com.aldebaran.qi.sdk.object.human.PleasureState;
import com.aldebaran.qi.sdk.object.human.SmileState;
import com.aldebaran.qi.sdk.object.humanawareness.HumanAwareness;
import com.aldebaran.qi.sdk.object.image.EncodedImage;
import com.aldebaran.qi.sdk.object.image.EncodedImageHandle;
import com.aldebaran.qi.sdk.object.image.TimestampedImageHandle;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;
import com.aldebaran.qi.sdk.object.streamablebuffer.StreamableBuffer;
import com.softbankrobotics.dx.followme.FollowHuman;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {
    private Chat chat;


    // Store the GoTo action.
    private GoTo goTo;


    // Store the HumanAwareness service.
    private HumanAwareness humanAwareness;
    // The QiContext provided by the QiSDK.
    private QiContext qiContext;

    // The button used to start take picture action.
    private Button button;
    // An image view used to show the picture.
    private ImageView pictureView;
    // TimestampedImage future.
    private Future<TimestampedImageHandle> timestampedImageHandleFuture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        this.qiContext = qiContext;
        pepperMapping(qiContext);
        //pepperTakePicture();
        //makingPepperGoTo(qiContext);
        //followHuman(qiContext);
        //Topic topic = TopicBuilder.with(qiContext).withResource(R.raw.esimerkki).build(); //Gets topic file for chatbot to use
        List<Topic>topics=new ArrayList<>();
        //Create list to hold topics
        /*topics.add(TopicBuilder.with(qiContext).withResource(R.raw.esimerkki).build());
        //add topic to the list
        topics.add(TopicBuilder.with(qiContext).withResource(R.raw.food).build());
        //add topic to the list
        topics.add(TopicBuilder.with(qiContext).withResource(R.raw.introduction).build());*/
       /*
        topics.add(TopicBuilder.with(qiContext).withResource(R.raw.keskustelu).build());
        //add topic to the list
        QiChatbot qiChatbot = QiChatbotBuilder.with(qiContext).withTopics(topics).build();
        //Creates QiChatbot for Chat to use
        Locale locale=new Locale(Language.FINNISH,Region.FINLAND);
        chat = ChatBuilder.with(qiContext).withChatbot(qiChatbot).withLocale(locale).build(); //Creates chat using the chatbot and gives it languege to speak
        chat.addOnStartedListener(() -> Log.i("Testi", "Discussion started.")); //adds listener for when chat is started and prints to log when it's going.
        Future<Void> chatFuture = chat.async().run(); //Starts the chat asyncronically
        //prints if chat has error starting
        chatFuture.thenConsume(future -> {
            if(future.hasError()){
                Log.e("Error", "Discussion finished with error.", future.getError());
            }
        });
        */
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
        /*
        Animation animation= AnimationBuilder.with(qiContext).withResources(R.raw.animation_00).build();
        //builds Animation
        Animate animate = AnimateBuilder.with(qiContext).withAnimation(animation).build();
        //builds runnable animation
        animate.run();
        //starts the animation
        */
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
        this.qiContext = null;
        if (goTo != null) {
            goTo.removeAllOnStartedListeners();
        }

    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }



    private void pepperTakePicture(){
        Future<TakePicture> takePictureFuture = TakePictureBuilder.with(qiContext).buildAsync();
        // Find the button and the imageView in the onCreate method
        button= (Button) findViewById(R.id.take_pic_button);
        pictureView = findViewById(R.id.picture_view);

        // Set the button onClick listener.
        button.setOnClickListener(v -> takePicture(takePictureFuture));
    }
    public void takePicture(Future<TakePicture> takePictureFuture) {
        String TAG="kuva";
        // Check that the Activity owns the focus.
        if (qiContext == null) {
            return;
        }

        // Disable the button.
        button.setEnabled(false);

        Future<TimestampedImageHandle> timestampedImageHandleFuture = takePictureFuture.andThenCompose(takePicture -> {
            Log.i(TAG, "take picture launched!");
            return takePicture.async().run();
        });timestampedImageHandleFuture.andThenConsume(timestampedImageHandle -> {
            // Consume take picture action when it's ready
            Log.i(TAG, "Picture taken");
            // get picture
            EncodedImageHandle encodedImageHandle = timestampedImageHandle.getImage();

            EncodedImage encodedImage = encodedImageHandle.getValue();
            Log.i(TAG, "PICTURE RECEIVED!");

            // get the byte buffer and cast it to byte array
            ByteBuffer buffer = encodedImage.getData();
            buffer.rewind();
            final int pictureBufferSize = buffer.remaining();
            final byte[] pictureArray = new byte[pictureBufferSize];
            buffer.get(pictureArray);

            Log.i(TAG, "PICTURE RECEIVED! (" + pictureBufferSize + " Bytes)");
            // display picture
            Bitmap pictureBitmap = BitmapFactory.decodeByteArray(pictureArray, 0, pictureBufferSize);
            runOnUiThread(() -> pictureView.setImageBitmap(pictureBitmap));
        });
    }


    private void testi(LocalizeAndMap localizeAndMap,QiContext qiContext){
        ExplorationMap explorationMap = localizeAndMap.dumpMap();
        Localize localize = LocalizeBuilder.with(qiContext)
                .withMap(explorationMap)
                .build();
        localize.addOnStatusChangedListener(localizationStatus -> {
            if (localizationStatus == LocalizationStatus.LOCALIZED) {
                /* robot is ready! */
            }
        });
        StreamableBuffer streamableBuffer = explorationMap.serializeAsStreamableBuffer();
        MapTopGraphicalRepresentation mapGraphicalRepresentation =
                explorationMap.getTopGraphicalRepresentation();
        EncodedImage encodedImage = mapGraphicalRepresentation.getImage();
        pictureView = findViewById(R.id.picture_view);
        byte[] decodedString = Base64.decode(encodedImage.getData().array(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        pictureView.setImageBitmap(decodedByte);

    }

    private Future<Void> localizationAndMapping;
    private void pepperMapping(QiContext qiContext){
// Build the action.
        LocalizeAndMap localizeAndMap = LocalizeAndMapBuilder.with(qiContext).build();
// Add a listener to get the map when localized.
        localizeAndMap.addOnStatusChangedListener(localizationStatus -> {
            if (localizationStatus == LocalizationStatus.LOCALIZED) {
                // Stop the action.
                localizationAndMapping.requestCancellation();
                // Dump the map for future use by a Localize action.
                ExplorationMap explorationMap = localizeAndMap.dumpMap();
                Log.i("heii",explorationMap.serialize());
            }
        });

// Run the action.
        localizationAndMapping = localizeAndMap.async().run();

    }

    private void followHuman(QiContext qiContext){
        this.qiContext=qiContext;
        humanAwareness = qiContext.getHumanAwareness();
        findHumansAround();
        /*Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(v -> {
            if (qiContext != null) {
                findHumansAround();
            }
        });*/
        PhraseSet phraseSet = PhraseSetBuilder.with(qiContext)
                .withTexts("Ihmiset")
                .build();
        Listen listen=ListenBuilder.with(qiContext).withLocale(new Locale(Language.FINNISH,Region.FINLAND)).withPhraseSet(
                phraseSet
        ).build();
        /*ListenResult listenResult=listen.run();
        if(listenResult.getHeardPhrase().getText().equals("Ihmiset")){
            followHuman(qiContext);
        }
        Log.i("TAG", "Heard phrase: " + listenResult.getHeardPhrase().getText());
*/
    }


    private void findHumansAround() {
        Future<List<Human>> humansAroundFuture=humanAwareness.async().getHumansAround();
        humansAroundFuture.andThenConsume(humansAround -> {
            Log.i("ihmisii", humansAround.size() + " human(s) around.");
            retrieveCharacteristics(humansAround);
        });
    }


    private void retrieveCharacteristics(final List<Human> humans) {

        String TAG="ihmis tietoo";
        for (int i = 0; i < humans.size(); i++) {
            // Get the human.
            Human human = humans.get(i);


            if(i==0){
                FollowHuman followHuman = new  FollowHuman(qiContext, human,null );
                followHuman.start();
            }

            // Get the characteristics.
            Integer age = human.getEstimatedAge().getYears();
            Gender gender = human.getEstimatedGender();
            PleasureState pleasureState = human.getEmotion().getPleasure();
            ExcitementState excitementState = human.getEmotion().getExcitement();
            EngagementIntentionState engagementIntentionState = human.getEngagementIntention();
            SmileState smileState = human.getFacialExpressions().getSmile();
            AttentionState attentionState = human.getAttention();

            // Display the characteristics.
            Log.i(TAG, "----- Human " + i + " -----");
            Log.i(TAG, "Age: " + age + " year(s)");
            Log.i(TAG, "Gender: " + gender);
            Log.i(TAG, "Pleasure state: " + pleasureState);
            Log.i(TAG, "Excitement state: " + excitementState);
            Log.i(TAG, "Engagement state: " + engagementIntentionState);
            Log.i(TAG, "Smile state: " + smileState);
            Log.i(TAG, "Attention state: " + attentionState);
        }


    }

    private void makingPepperGoTo(QiContext qiContext){
        String TAG="GOTO";
        Actuation actuation=qiContext.getActuation();
        Frame robotFrame=actuation.robotFrame();
        //Transform transform= TransformBuilder.create().fromXTranslation(-0.5);
        Transform transform= TransformBuilder.create().from2DTranslation(0,-0.5);
        Mapping mapping=qiContext.getMapping();
        FreeFrame targetFrame=mapping.makeFreeFrame();
        targetFrame.update(robotFrame,transform,0L);
        goTo= GoToBuilder.with(qiContext).withFrame(targetFrame.frame()).build();
        goTo.addOnStartedListener(() -> Log.i(TAG, "GoTo action started."));
        Future<Void> goToFuture=goTo.async().run();
        goToFuture.thenConsume(future -> {
            if (future.isSuccess()) {
                Log.i(TAG, "GoTo action finished with success.");
            } else if (future.hasError()) {
                Log.e(TAG, "GoTo action finished with error.", future.getError());
            }
        });
        goTo = GoToBuilder.with(qiContext)
                .withFrame(targetFrame.frame())
                .withMaxSpeed(0.2f) // Set the max speed.
                .build();
    }




}