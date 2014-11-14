package com.scommix.animation;

public enum Techniques {

    DropOut(DropOutAnimator.class),
    Landing(LandingAnimator.class),
    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    HingeAnimator(HingeAnimator.class),
    BounceInAnimator(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInUP(BounceInUpAnimator.class),
    FadeInRight(FadeInRightAnimator.class),
    Zoominup(ZoomInUpAnimator.class),
    shakeanimator(ShakeAnimator.class);

     Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
