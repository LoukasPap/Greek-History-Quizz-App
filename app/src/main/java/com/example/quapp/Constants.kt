package com.example.quapp

object Constants {

    fun getQuestions(): ArrayList<Question> {
        var questionList = ArrayList<Question>()

        val q1 = Question(0, "Το ξεκίνημα της επανάστασης, έγινε στις 26 Μαρτίου.", true, points = 100)
        val q2 = Question(1, "Η επανάσταση άρχισε το 1821;", true, points = 50)
        val q3 = Question(2, "Ο Θεόδωρος Κολοκοτρώνης έμεινε γνωστός και ως 'Νέγρος του Μωριά'.", false, points = 100)

        questionList.add(q1)
        questionList.add(q2)
        questionList.add(q3)

        return questionList
    }
}