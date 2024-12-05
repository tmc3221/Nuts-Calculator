import requests

# Replace with the actual URL of your Spring Boot server
BASE_URL = "http://localhost:8080"

def test_get_endpoint():
    response = requests.get(f"{BASE_URL}/bestHand ?cards=2H,3D,5S,9C,KD")
    print("GET /bestHand response:", response.json())
    assert response.status_code == 200

def test_post_endpoint():
    payload = {"name": "test"}
    response = requests.post(f"{BASE_URL}/bestHand", json=payload)
    print("POST /bestHand response:", response.json())
    assert response.status_code == 201

if __name__ == "__main__":
    test_get_endpoint()
    test_post_endpoint()
