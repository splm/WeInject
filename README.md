WeInject
========
About me:
  First,Iam from China,my English is poll.maybe you find my codes are like freshman's or some of fucking shit
,but please respect my work.

The Version:
0.0.1

The reference libarys and thinking is from the internet and n-bomb‘s blog.

Now,let me show how to use this frame in your code:
for example:
1.Inital the frame like following code at the base-activity or the activity which you want to use,

InitViewject.getInstance().init(context);

2.let's Go to the activity:
you gonna inital the target view of Button:

@viewInject(id=R.id.test_btn)
private Button test_btn;

Until now,you have already finished the inital job.  

3.How to bind an event?
Define the method:

public void methodName(View v){

  what are you doing?

}

@viewInject(id=R.id.test_btn,click="onClick")
private Button test_btn;

finish!

The more are in the codes!

enjoy it!
