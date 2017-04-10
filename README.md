# Upload Manager: Standalone Web Service to manage file uploads

## Overview
---
The goal of this project was to create a rudimentary file management system for easy deployment and modification. Currently, all uploads are saved to a single folder and versioning is not supported. However, with a few simple modifications to the model package, that can be easily remedied. 

## Endpoints
* GET **/**                                   
Landing Page
* GET **/all**                                
Lists all files
* POST **/upload/{owner}/{description}**      Method for posting file 
* GET **/details/{id}**                       Getting file details by file id
* GET **/details/filename/{filename}/**       Getting file details by filename
* GET **/recent**                             Getting all files uploaded in the last hour
* GET **/stream/{id}**                        Getting file stream by file id
* GET **/stream/filename/{filename}/**        Getting file stream by filename
* GET **/owner/{owner} **                     Getting all files uploaded by a given owner

## Scheduled Events
* Email
** This service sends out an email every 60 minutes of all files uploaded in the last 60 minutes

## Dependencies
* Spring Boot 2.0.0 SNAPSHOT
* Lombok
