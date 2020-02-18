package ru.mironow.sentiment_analysis

/**
 * State execution claim
 *
 * Created By Alexander Mironow - 14.02.2020
 */
enum class AnalysisClaimStage {
    PENDING, //pending execution
    LOAD_DATA, //load data
    EXECUTE, //execution claim
    FINISH, //finish
    ERROR //error
}