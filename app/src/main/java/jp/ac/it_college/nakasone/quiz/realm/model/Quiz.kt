package jp.ac.it_college.nakasone.quiz.realm.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Quiz : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var question: String = ""
    var imageFilename: String? = null
    var imageCopyright: String? = null
    var choices: RealmList<String> = RealmList()
}