import React, { useState } from "react";
import axios from "axios";
import "./App.css";

const App = () => {
  const [cards, setCards] = useState([""]);
  const [bestHand, setBestHand] = useState(null);
  const [error, setError] = useState("");

  const handleCardChange = (index, value) => {
    const updatedCards = [...cards];
    updatedCards[index] = value;
    setCards(updatedCards);
  };

  const addCardField = () => {
    setCards([...cards, ""]);
  };

  const removeCardField = (index) => {
    const updatedCards = cards.filter((_, idx) => idx !== index);
    setCards(updatedCards);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("/api/poker/bestHand", { cards });
      setBestHand(response.data.bestHand);
      setError("");
    } catch (error) {
      setError("Error calculating best hand. Ensure valid card format.");
      setBestHand(null);
    }
  };

  return (
    <div className="App">
      <h1>Poker Hand Evaluator</h1>
      <form onSubmit={handleSubmit}>
        {cards.map((card, index) => (
          <div key={index} className="card-input">
            <input
              type="text"
              placeholder="e.g., 10H, QS"
              value={card}
              onChange={(e) => handleCardChange(index, e.target.value)}
            />
            <button type="button" onClick={() => removeCardField(index)}>
              Remove
            </button>
          </div>
        ))}
        <button type="button" onClick={addCardField}>
          Add Card
        </button>
        <button type="submit">Evaluate Hand</button>
      </form>
      {bestHand && <h2>Best Hand: {bestHand}</h2>}
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default App;
