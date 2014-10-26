WeInject
========
About me:
---
  First,Iam from China,my English is poll.maybe you find my codes are like freshman's or some fucking shit
,but please respect my work.

The Version:
0.0.1

Example:
----
`1.Initial the frame like following code at the base-activity or the activity which you want to use`
```Java
InitViewject.getInstance().init(context);
```
`2.let's Go to the activity what you want to deal`<br>
Set the activity layout resource<br>
```Java
@ContentViewInject(layout resource id)
public class MainActivity extends Activity
```
Initial the target view of Button:
```Java
@viewInject(id=R.id.test_btn)
private Button test_btn;
```
Until now,you have already finished the inital job.  

`3.How to bind an event?`
Define the method:
```Java
public void methodName(View v){

    //what are you doing?

}

@viewInject(id=R.id.test_btn,click="onClick")
private Button test_btn;
```

enjoy it!
