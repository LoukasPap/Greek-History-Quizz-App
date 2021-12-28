package com.example.quapp

object Constants {

    fun getQuestions(): ArrayList<Question> {
        var questionList = ArrayList<Question>()

        val q1 = Question(0, "5 + 5 = 10", true)
        val q2 = Question(1, "Η επανάσταση έγινε το 1821;", true)
        val q3 = Question(2, "Ο Θεόδωρος Κολοκοτρώνης έμεινε γνωστός και ως 'Νέγρος του Μωριά'.", false)

        questionList.add(q1)
        questionList.add(q2)
        questionList.add(q3)

        return questionList
    }
}