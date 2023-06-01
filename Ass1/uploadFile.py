# Import necessary libraries
import flask 
from flask import Flask, render_template, request
import os
import json

# Create a Flask instance
app = Flask(__name__)

# Connect Python and HTML
# Define a route to the HTML file
@app.route('/', methods=['GET'])
def index():
    return render_template('index.html')

# Create an API endpoint to POST file
@app.route('/file-upload', methods=['POST'])
def upload_file():
    # Get the file from the request
    file = request.files['file']

    # Check if the file is present or not
    if file:
        # Create a directory if it doesn't exist
        if not os.path.exists('data'):
            os.makedirs('data')

        # Store the file in the directory
        file.save(os.path.join('data', file.filename))

        # Return a response with file name
        response = {'message': 'File ' + file.filename + ' uploaded successfully'}
        return json.dumps(response)

# Run the app
if __name__ == '__main__':
    app.run(debug=True)