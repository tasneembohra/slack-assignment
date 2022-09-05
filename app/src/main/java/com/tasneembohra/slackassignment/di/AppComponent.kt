package com.tasneembohra.slackassignment.di

import com.tasneembohra.slackassignment.network.di.networkModules
import com.tasneembohra.slackassignment.repo.db.di.databaseModule
import com.tasneembohra.slackassignment.repo.di.repositoriesModules


val appModules = repositoriesModules + databaseModule + networkModules
