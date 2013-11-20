//~--- non-JDK imports --------------------------------------------------------

import com.github.robocup_atan.atan.model.ActionsPlayer;
import com.github.robocup_atan.atan.model.ControllerPlayer;
import com.github.robocup_atan.atan.model.enums.Errors;
import com.github.robocup_atan.atan.model.enums.Flag;
import com.github.robocup_atan.atan.model.enums.Line;
import com.github.robocup_atan.atan.model.enums.Ok;
import com.github.robocup_atan.atan.model.enums.PlayMode;
import com.github.robocup_atan.atan.model.enums.RefereeMessage;
import com.github.robocup_atan.atan.model.enums.ServerParams;
import com.github.robocup_atan.atan.model.enums.ViewAngle;
import com.github.robocup_atan.atan.model.enums.ViewQuality;
import com.github.robocup_atan.atan.model.enums.Warning;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------


import java.util.HashMap;
import java.util.Random;

/**
 * A simple controller. It implements the following simple behaviour. If the
 * client sees nothing (it might be out of the field) it turns 180 degree. If
 * the client sees the own goal and the distance is less than 40 and greater
 * than 10 it turns to his own goal and dashes. If it cannot see the own goal
 * but can see the ball it turns to the ball and dashes. If it sees anything but
 * not the ball or the own goals it dashes a little bit and turns a fixed amount
 * of degree to the right.
 *
 * @author Atan
 */
public class Simple implements ControllerPlayer {
    private static int    	count         	= 0;
    private static Logger 	log          	= Logger.getLogger(Simple.class);
    private Random        	random        	= null;
    private boolean       	canSeeOwnGoal 	= false;
    private boolean       	canSeeNothing 	= true;
    private boolean       	canSeeBall    	= false;
    private double        	directionBall;
    private double        	directionOwnGoal;
    private double       	distanceBall;
    private double        	distanceOwnGoal;
    private ActionsPlayer 	player;
    //i used to turn player's head every preInfo().
    private int			i 		= 1;
    //Type of player added
    private String 		type;
    //fields for holding opponent's goal information
    private double 		directionOtherGoal;
    private double 		distanceOtherGoal;
    //fixed values for dashing keep stamina under control
    private	int		fastDash 	= 70;
    private	int	  	slowDash 	= 40;
    //The following fields hold location information
    
    
    //private ControllerPlayer x;	

    /**
     * Constructs a new simple client.
     */
    public Simple() {
        random = new Random(System.currentTimeMillis() + count);
        count++;
    }

    /** {@inheritDoc} */
    @Override
    public ActionsPlayer getPlayer() {
        return player;
    }

    /** {@inheritDoc} 
     * turns player neck 40degres to the right to begin with so 
     * player will turn head evenly, compared to body, 
     every preInfo().*/
    @Override
    public void setPlayer(ActionsPlayer p) {
        player = p;
        player.turnNeck(40);
    }

    /** {@inheritDoc} 
    *Resets canSeeOwnGoal, canSeeBall, and canSeeNothing so that
    *if the player has lost track of it's own goal or the ball since 
    *the last preInfo() it will default to seeing nothing.
    *Also alternately turns the player's head right and left by 80degrees.*/
    @Override
    public void preInfo() {
    	canSeeOwnGoal = false;
        canSeeBall    = false;
        canSeeNothing = true;
    	if(i%2 == 1){
    		player.turnNeck(-80);
    	} else {
    		player.turnNeck(80);
    	}
    	i++;  
    }

    /** {@inheritDoc} */
    @Override
    public void postInfo() {
    	if(this.getType().equals("Goalie")){
    		this.getPlayer().dash(100);
    	} else {
    		if(this.getType().equals("Defender")){
    			
    		} else {
    			if(this.getType().equals("Midfielder")){
    			
    			} else {
    				if(this.getType().equals("Attacker")){
    			
    				}
    			}
    		}
    	}
    	/**
        if (canSeeNothing) {
            canSeeNothingAction();
        } else if (canSeeOwnGoal) {
        	//decreased distance players must be from own goal before canSeeOwnGoalAction is called.
            if ((distanceOwnGoal < 25) && (distanceOwnGoal > 10)) {
                canSeeOwnGoalAction();
            } else if (canSeeBall) {
                canSeeBallAction();
            } else {
                canSeeAnythingAction();
            }
        } else if (canSeeBall) {
            canSeeBallAction();
        } else {
            canSeeAnythingAction();
        }
        */
        
        
        /** 
         * if (distBall < 10) {
            getPlayer().turn(10.0);
            getPlayer().dash(5);
        } else if (minDistLine < 10) {
            getPlayer().turn(-90.0);
        } else {
            getPlayer().dash(50);
        }
        log.info("I am a silly Client");
        */
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagRight(Flag flag, double distance, double direction, double distChange, double dirChange,
                                 double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
        
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagLeft(Flag flag, double distance, double direction, double distChange, double dirChange,
                                double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
                               double bodyFacingDirection, double headFacingDirection) {
    	canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagOther(Flag flag, double distance, double direction, double distChange, double dirChange,
                                 double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagCenter(Flag flag, double distance, double direction, double distChange, double dirChange,
                                  double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagCornerOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
                                     double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagCornerOther(Flag flag, double distance, double direction, double distChange,
                                       double dirChange, double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagPenaltyOwn(Flag flag, double distance, double direction, double distChange,
                                      double dirChange, double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagPenaltyOther(Flag flag, double distance, double direction, double distChange,
            double dirChange, double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagGoalOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
                                   double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
        if (flag == Flag.CENTER) {
            this.canSeeOwnGoal    = true;
            this.distanceOwnGoal  = distance;
            this.directionOwnGoal = direction;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeFlagGoalOther(Flag flag, double distance, double direction, double distChange, double dirChange,
                                     double bodyFacingDirection, double headFacingDirection) {
    	canSeeNothing = false;
    	directionOtherGoal = direction;
    	distanceOtherGoal = distance;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeeLine(Line line, double distance, double direction, double distChange, double dirChange,
                            double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing = false;
    }

    /** {@inheritDoc} */
    @Override
    public void infoSeePlayerOther(int number, boolean goalie, double distance, double direction, double distChange,
                                   double dirChange, double bodyFacingDirection, double headFacingDirection) {}

    /** {@inheritDoc} */
    @Override
    public void infoSeePlayerOwn(int number, boolean goalie, double distance, double direction, double distChange,
                                 double dirChange, double bodyFacingDirection, double headFacingDirection) {}

    /** {@inheritDoc} */
    @Override
    public void infoSeeBall(double distance, double direction, double distChange, double dirChange,
                            double bodyFacingDirection, double headFacingDirection) {
        canSeeNothing      = false;
        this.canSeeBall    = true;
        this.distanceBall  = distance;
        this.directionBall = direction;
        
    }

    /** {@inheritDoc} */
    @Override
    public void infoHearReferee(RefereeMessage refereeMessage) {}

    /** {@inheritDoc} */
    //added player repositioning on the field in case of goal scored by either side.
    @Override
    public void infoHearPlayMode(PlayMode playMode) {
        if ((playMode == PlayMode.BEFORE_KICK_OFF) || (playMode == PlayMode.GOAL_OWN) || (playMode == PlayMode.GOAL_OTHER)) {
            this.pause(1000);
            switch (this.getPlayer().getNumber()) {
                case 1 :
                    this.getPlayer().move(-45, 0);
                    this.setType("Goalie");
                    break;
                case 2 :
                    this.getPlayer().move(-10, 10);
                    this.setType("Attacker");
                    break;
                case 3 :
                    this.getPlayer().move(-10, -10);
                    this.setType("Attacker");
                    break;
                case 4 :
                    this.getPlayer().move(-20, 0);
                    this.setType("Attacker");
                    break;
                case 5 :
                    this.getPlayer().move(-20, 10);
                    this.setType("Midfielder");
                    break;
                case 6 :
                    this.getPlayer().move(-20, -10);
                    this.setType("Midfielder");
                    break;
                case 7 :
                    this.getPlayer().move(-20, 20);
                    this.setType("Midfielder");
                    break;
                case 8 :
                    this.getPlayer().move(-20, -20);
                    this.setType("Midfielder");
                    break;
                case 9 :
                    this.getPlayer().move(-30, 0);
                    this.setType("Defender");
                    break;
                case 10 :
                    this.getPlayer().move(-40, 10);
                    this.setType("Defender");
                    break;
                case 11 :
                    this.getPlayer().move(-40, -10);
                    this.setType("Defender");
                    break;
                default :
                    throw new Error("number must be initialized before move");
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    //awesome. keep distances.
    public void infoHearPlayer(double direction, String message) {}

    /** {@inheritDoc} */
    @Override
    public void infoSenseBody(ViewQuality viewQuality, ViewAngle viewAngle, double stamina, double unknown,
                              double effort, double speedAmount, double speedDirection, double headAngle,
                              int kickCount, int dashCount, int turnCount, int sayCount, int turnNeckCount,
                              int catchCount, int moveCount, int changeViewCount) {}

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return type;
    }

    /** {@inheritDoc} */
    @Override
    public void setType(String newType) {}

    /** {@inheritDoc} */
    @Override
    public void infoHearError(Errors error) {}

    /** {@inheritDoc} */
    @Override
    public void infoHearOk(Ok ok) {}

    /** {@inheritDoc} */
    @Override
    public void infoHearWarning(Warning warning) {}

    /** {@inheritDoc} */
    @Override
    public void infoPlayerParam(double allowMultDefaultType, double dashPowerRateDeltaMax,
                                double dashPowerRateDeltaMin, double effortMaxDeltaFactor, double effortMinDeltaFactor,
                                double extraStaminaDeltaMax, double extraStaminaDeltaMin,
                                double inertiaMomentDeltaFactor, double kickRandDeltaFactor,
                                double kickableMarginDeltaMax, double kickableMarginDeltaMin,
                                double newDashPowerRateDeltaMax, double newDashPowerRateDeltaMin,
                                double newStaminaIncMaxDeltaFactor, double playerDecayDeltaMax,
                                double playerDecayDeltaMin, double playerTypes, double ptMax, double randomSeed,
                                double staminaIncMaxDeltaFactor, double subsMax) {}

    /** {@inheritDoc} */
    @Override
    public void infoPlayerType(int id, double playerSpeedMax, double staminaIncMax, double playerDecay,
                               double inertiaMoment, double dashPowerRate, double playerSize, double kickableMargin,
                               double kickRand, double extraStamina, double effortMax, double effortMin) {}

    /** {@inheritDoc} */
    @Override
    public void infoCPTOther(int unum) {}

    /** {@inheritDoc} */
    @Override
    public void infoCPTOwn(int unum, int type) {}

    /** {@inheritDoc} */
    @Override
    public void infoServerParam(HashMap<ServerParams, Object> info) {}

    /**
     * This is the action performed when the player can see the ball.
     * It involves running at it and kicking it...
     */
    //substituted for fastDash
    private void canSeeBallAction() {
        if (distanceBall < 0.7) {
            getPlayer().kick(50, directionOtherGoal);
        } else {
        	if (distanceBall < 25) {
        		getPlayer().dash(fastDash);
        	} else {
        	}
        }
        getPlayer().dash(fastDash);
        turnTowardBall();
        if (log.isDebugEnabled()) {
            log.debug("b(" + directionBall + "," + distanceBall + ")");
        }
    }

    /**
     * If the player can see anything that is not a ball or a goal, it turns.
     */
    //substituted for fastDash
    private void canSeeAnythingAction() {
        getPlayer().dash(0);
        if (log.isDebugEnabled()) {
            log.debug("a");
        }
    }

    /**
     * If the player can see nothing, it turns 180 degrees.
     */
    private void canSeeNothingAction() {
        getPlayer().turn(180);
        if (log.isDebugEnabled()) {
            log.debug("n");
        }
    }

    /**
     * If the player can see its own goal, it goes and stands by it...
     */
    //new fastDash and slowDash fields. Substituted random values in order to get stamina under control.
    private void canSeeOwnGoalAction() {
        getPlayer().dash(fastDash);
        turnTowardOwnGoal();
        if (log.isDebugEnabled()) {
            log.debug("g(" + directionOwnGoal + "," + distanceOwnGoal + ")");
        }
    }

    /**
     * Randomly choose a fast dash value.
     * @return
     *
    private int randomDashValueFast() {
        return 30 + random.nextInt(100);
    }

    /**
     * Randomly choose a slow dash value.
     * @return
     *
    private int randomDashValueSlow() {
        return -10 + random.nextInt(50);
    }
	*/
    
    /**
     * Turn towards the ball.
     */
    private void turnTowardBall() {
        getPlayer().turn(directionBall);
        
    }

    /**
     * Turn towards our goal.
     */
    private void turnTowardOwnGoal() {
        getPlayer().turn(directionOwnGoal);
    }

    /**
     * Randomly choose a kick direction.
     * @return
     *
    private int randomKickDirectionValue() {
        return -45 + random.nextInt(90);
    }
    */

    /**
     * Pause the thread.
     * @param ms How long to pause the thread for (in ms).
     */
    private synchronized void pause(int ms) {
        try {
            this.wait(ms);
        } catch (InterruptedException ex) {
            log.warn("Interrupted Exception ", ex);
        }
    }
}
