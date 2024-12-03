import requests
import json

# URL of the Java REST API
url = "http://localhost:8080/api/poker/bestHand"
headers = {"Content-Type": "application/json"}

# Sample input cards
'''data = {
    "cards": ["AH", "KH", "2D", "3C", "5S"]
}'''

# Send POST request
response = requests.post(url, headers=headers, data=json.dumps(data))

# Handle the response
if response.status_code == 200:
    best_hand = response.text
    print(f"The best hand (NUTS) is: {best_hand}")
else:
    print(f"Failed to get the best hand. Status code: {response.status_code}")
