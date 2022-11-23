def BRANCH_NAME = ''

pipeline
{
      agent any
      
      options
      {
            // Append timestamps to the console output.
            timestamps()

            // Disable concurrent builds.
            disableConcurrentBuilds()

            // Discard old builds.     
            buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '10'))
      }

      stages
      {
           stage('Checkout Source')
            {
                  steps
                  {
                        deleteDir()

                        checkout scm
                  }
            }

            stage('Trigger AppCenter Build')
            {
                  environment
                  {
                        APPCENTER_API_TOKEN = credentials('client-appcenter-token')
                       
                  }
                  steps
                  {
                       
                        appcenter build queue --branch master --debug-logs --app paridhi.keshri-nagarro.com/SampleAndroidApp --token ${APPCENTER_API_TOKEN}
                 
                  }
            }
      }
}
