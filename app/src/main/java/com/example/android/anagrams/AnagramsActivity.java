package com.example.android.anagrams;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static  int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    ArrayList<String> newdic=new ArrayList<String>();
    ArrayList<String> im=new ArrayList<String>();
    ArrayList<String> wordList = new ArrayList<String>();
    private ArrayList<String> newresult=new ArrayList<String>();
    int y;
    ArrayList<String> result;
    int size2;



    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        result= new ArrayList<String>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
        }
        y = wordList.size();
        for (int w=0;w<y;w++){
            if((wordList.get(w).length())>3){
                newdic.add(wordList.get(w));
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        if((word.toLowerCase().compareTo(base.toLowerCase())!=0)) {
            if (dictionarycheck(word) == true) {
                if (word.toLowerCase().length() == base.toLowerCase().length() ||(word.toLowerCase().length() == (base.toLowerCase().length()+1))) {
                    int size = result.size();
                    int i = 0;
                    for (i = 0; i < size; i++) {
                        if (word.toLowerCase().equals(result.get(i).toLowerCase())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        int i = 0;
        result.clear();
        char x[] = targetWord.toLowerCase().toCharArray();
        Arrays.sort(x);
        String p = new String(x);
        char y[];
        String q;
        for (i=0;i<this.y;i++){
            y= wordList.get(i).toLowerCase().toCharArray();
            Arrays.sort(y);
            q= new String(y);
            if((wordList.get(i).toLowerCase().equals(targetWord.toLowerCase()))) {
            }
            else{
                if(p.equals(q)){
                    result.add(wordList.get(i));
                }
            }
        }
        //ArrayList<String> result = new ArrayList<String>();
        size2=result.size();
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String targetword) {
        char d;
        int y;
        ArrayList<String> onemore = new ArrayList<String>();
        int i;
        String g;
        for(d='a';d<='z';d++){
            g = d +targetword;
            onemore = getAnagrams(g);
            y=onemore.size();
            for(i=0;i<y;i++){
                if(((onemore.get(i)).compareTo((targetword+d))) !=0){
                    newresult.add(onemore.get(i));
                }
            }
        }
        int x = newresult.size();
        for(i=0;i<x;i++){
            if(dictionarycheck(newresult.get(i))==false){
                newresult.remove(i);
                x--;
            }
        }
        return newresult;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public String pickGoodStarterWord() {
        int m1,s;
        while (true){
            s=newdic.size();
            m1= ThreadLocalRandom.current().nextInt(s);
            if((newdic.get(m1).length())== DEFAULT_WORD_LENGTH){
                result=getAnagrams(newdic.get(m1));
                im=getAnagramsWithOneMoreLetter(newdic.get(m1));
                result.addAll(im);
                if((result.size())>=MIN_NUM_ANAGRAMS){
                    result.clear();
                    im.clear();
                    if(DEFAULT_WORD_LENGTH<MAX_WORD_LENGTH) {
                        DEFAULT_WORD_LENGTH++;
                    }
                    else DEFAULT_WORD_LENGTH=4;
                    return newdic.get(m1);

                }else newdic.remove(m1);
            }
            else newdic.remove(m1);
        }

    }
    public boolean dictionarycheck(String y){
        int low=0;
        int high=this.y;
        int mid;
        while(high>=low){
            mid=(low+high)/2;
            if(y.toLowerCase().compareTo(wordList.get(mid).toLowerCase())>0){
                low=mid+1;
            }
            else if(y.toLowerCase().compareTo(wordList.get(mid).toLowerCase())<0){
                high=mid-1;
            }
            else
            {
                if(y.toLowerCase().compareTo(wordList.get(mid).toLowerCase())==0){
                    return true;
                }
            }
        }
        return false;
    }
}


